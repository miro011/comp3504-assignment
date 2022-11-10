# pip install flask
# pip install mysql-connector-python
# pip install dotenv

# HELPFUL STUFF:
    # https://www.youtube.com/watch?v=5ZMpbdK0uqU
    # https://www.programcreek.com/python/example/64945/flask.request.get_json

##########################################################
# IMPORTS

import json, re, os

from flask import *
from dotenv import load_dotenv

import mysql.connector
from mysql.connector import errorcode

##########################################################
# GLOBALS

load_dotenv()

print(os.getenv('TEST'))

APP = Flask(__name__)

DB = None
DB_CONFIG = {
  'user': os.getenv('DATABASE_USER'),
  'password': os.getenv('DATABASE_PASSWORD'),
  'host': os.getenv('DATABASE_HOST'),
  'database': os.getenv('DATABASE_NAME')
}

INFO = """GET /items/ => Shows a list of all items
GET /items/?id=num => Search for an item by id
GET /items/?name=somename => Search for an item by name
POST /items/ => {'id':'num', 'name':'somename', 'qty':'num', 'price':'num.num', 'sid':'num'} => Add new item
PUT /items/ => {'id':'num', 'qty':'num'} => Update the quantity of an item
DELETE /items/ => {'id':'num'} => Delete an item given its id"""

##########################################################
# ROUTES

#---------------------------------------------------------
# Base

@APP.route("/")
def home_page():
    if request.method != "GET":
        return abort(400, "Base route is GET only and it provides infomration about this API")

    # trick to display new lines in response
    return Markup(INFO.replace("\n", "<br>"))

#---------------------------------------------------------
# Look up items

@APP.route('/items/', methods = ['GET'])
def get_items():
    # FIGURE STUFF OUT
    argsDict = request.args
    prepedStatementStr = ""
    valuesArr = []

    # list of all items
    if len(argsDict) == 0:
        prepedStatementStr = "SELECT * from items"

    # searching for items
    elif len(argsDict) == 1:
        expectedKeysArr = argsDict.keys()
        if "id" not in expectedKeysArr and "name" not in expectedKeysArr:
            return abort(400, "Items can only be searched using their name or id")

        validationResultsArr = arguments_validation(argsDict, expectedKeysArr)
        if len(validationResultsArr) > 0:
            return abort(400, Markup("<br>".join(validationResultsArr)))

        if "id" in expectedKeysArr:
            prepedStatementStr = "SELECT * FROM items WHERE itemID=%s"
            valuesArr.append(argsDict['id'])
        elif "name" in expectedKeysArr:
            prepedStatementStr = "SELECT * FROM items WHERE itemName=%s"
            valuesArr.append(argsDict['name'])
    else:
        return abort(400, "Only 1 argument (id/name) for searching items expected")

    # RESPONSE
    response = talk_to_db(prepedStatementStr, valuesArr)
    if isinstance(response, list): return json.dumps(response)
    else: return abort(400, "error with the API")

#---------------------------------------------------------
# Add new item

# TESTING:
# curl -X POST http://127.0.0.1:7777/items/ --header "Content-Type:application/json" --data '{"id":"2", "name":"test2", "qty":"12", "price":"12.10", "sid":"50001"}'
@APP.route('/items/', methods = ['POST'])
def add_new_item():
    argsDict = request.get_json()
    stip_dict(argsDict)

    validationResultsArr = arguments_validation(argsDict, ["id", "name", "qty", "price", "sid"], True)
    if len(validationResultsArr) > 0:
        return abort(400, Markup("<br>".join(validationResultsArr)))

    #return f"Add item {argsDict} using database operations"
    prepedStatementStr = "INSERT INTO items VALUES (%s, %s, %s, %s, %s)"
    valuesArr = [argsDict["id"], argsDict["name"], argsDict["qty"], argsDict["price"], argsDict["sid"]]

    insertionSuccessful = talk_to_db(prepedStatementStr, valuesArr)

    if insertionSuccessful: return jsonify(success=True) # a way to return successful message with Flask
    else: return abort(400, "error with the API")

#---------------------------------------------------------
# Update item quanity

# TESTING:
# curl -X PUT http://127.0.0.1:7777/items/ --header "Content-Type:application/json" --data '{"id":"3000", "qty":"12"}'
@APP.route('/items/', methods = ['PUT'])
def update_item_quantity():
    argsDict = request.get_json()
    stip_dict(argsDict)

    validationResultsArr = arguments_validation(argsDict, ["id", "qty"])
    if len(validationResultsArr) > 0:
        return abort(400, Markup("<br>".join(validationResultsArr)))

    updateSuccessful = talk_to_db("UPDATE items SET quantity=%s WHERE itemID=%s", [argsDict["qty"], argsDict["id"]])

    if updateSuccessful: return jsonify(success=True) # a way to return successful message with Flask
    else: return abort(400, "error with the API")

#---------------------------------------------------------
# Delete item

# TESTING:
# curl -X DELETE http://127.0.0.1:7777/items/ --header "Content-Type:application/json" --data '{"id":"3000"}'
@APP.route('/items/', methods = ['DELETE'])
def delete_item():
    argsDict = request.get_json()
    stip_dict(argsDict)

    validationResultsArr = arguments_validation(argsDict, ["id"])
    if len(validationResultsArr) > 0:
        return abort(400, Markup("<br>".join(validationResultsArr)))

    deletionSuccessful = talk_to_db("DELETE FROM items WHERE itemID=%s", [argsDict["id"]])

    if deletionSuccessful: return jsonify(success=True) # a way to return successful message with Flask
    else: return abort(400, "error with the API")

##########################################################
# HELPERS

#---------------------------------------------------------
# Validators

# check if the arguments (dict) is valid
# checks the length and validation of individual arguments, incl from database
# returns an array containg issues
def arguments_validation(dictRef, expectedKeysArr, addingItem=False):
    result = []

    if len(dictRef.keys()) < len(expectedKeysArr):
        return ["Not enough arguments provided."]
    elif len(dictRef.keys()) > len(expectedKeysArr):
        return ["Too many arguments provided."]
    elif sorted(dictRef.keys()) != sorted(expectedKeysArr):
        return ["Unexpected arguments present."]

    if "id" in expectedKeysArr:
        if not is_digit(dictRef["id"]):
            result.append("Invalid id format.")
        else:
            response = talk_to_db("SELECT itemID FROM items WHERE itemID=%s", [dictRef["id"]])
            if addingItem and response:
                result.append("Item already exists.")
            elif not addingItem and not response:
                result.append("Nonexistent id.")
    if "name" in expectedKeysArr:
        if not is_letters(dictRef["name"]):
            result.append("Invalid name format.")
        else:
            response = talk_to_db("SELECT itemID FROM items WHERE itemName=%s", [dictRef["name"]])
            # assuming 2 items with the same name but different id can be added
            if not addingItem and not response:
                result.append("Nonexistent name.")
    if "qty" in expectedKeysArr and not is_digit(dictRef["qty"]):
        result.append("Invalid qty format.")
    if "price" in expectedKeysArr and not is_number(dictRef["price"]):
        result.append("Invalid price format.")
    if "sid" in expectedKeysArr:
        if not is_digit(dictRef["sid"]):
            result.append("Invalid supplier id format.")
        else:
            response = talk_to_db("SELECT supplierID FROM supplier WHERE supplierID=%s", [dictRef["sid"]])
            if not response:
                result.append("Nonexistent sid.")

    return result

def is_digit(str):
    return True if re.match(r"^\d+$", str) else False

def is_number(str):
    return True if re.match(r"^[0-9.]+$", str) else False

def is_letters(str):
    return True if re.match(r"^[a-zA-Z ]+$", str) else False

#---------------------------------------------------------
# Database

# https://dev.mysql.com/doc/connector-python/en/connector-python-example-connecting.html
def connect_to_db():
    global DB

    try:
        DB = mysql.connector.connect(**DB_CONFIG)
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
            print("Something is wrong with your user name or password")
        elif err.errno == errorcode.ER_BAD_DB_ERROR:
            print("Database does not exist")
        else:
            print(err)
        quit()
    except Exception as e:
        print (e)
        quit()

# prepedStatementStr, replace any values with %s
# valuesArr, provide the values in order (if no values, empty list)
# ex. talk_to_db("SELECT * FROM items WHERE itemID=%s", [someId])
# returns:
    # SELECT: False (improper SQL command), an array of dictionaries (rows), or an empty array (if empty response)
    # Everything else: False (improper SQL command), True (executed successfully)
def talk_to_db(prepedStatementStr, valuesArr=None):
    prepedStatementStr = prepedStatementStr.strip()
    if not valuesArr: valuesArr = []

    cursor = DB.cursor()

    try:
        cursor.execute(prepedStatementStr, valuesArr)
    except Exception as e:
        print(e)
        return False

    result = None

    if prepedStatementStr.lower().startswith("select"):
        result = []
        # cursor.description returns the row names => [("rowName", "otherInfo"), (rowName, "otherInfo"), ...]
        # have to call and copy cursor.description before fetchall() because fetchall() seems to screw it up
        rowNamesArr = []
        for rowInfoArr in cursor.description:
            rowNamesArr.append(rowInfoArr[0])

        rowsArr = cursor.fetchall()
        for rowDataArr in rowsArr:
            rowDict = {}
            for i in range(len(rowNamesArr)):
                rowDict[rowNamesArr[i]] = rowDataArr[i]
            result.append(rowDict)
    else:
        result = True
        # by default Connector/Python does not autocommit, it is important to call this method after every transaction that modifies data for tables
        DB.commit()

    cursor.close()
    return result


##########################################################
# GENERIC HELPERS

def stip_dict(dictRef):
    for key, value in dictRef.items():
        dictRef[key] = value.strip()

##########################################################
# RUN

connect_to_db()
APP.run(port=7777)
