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

APP = Flask(__name__)

DB = None
DB_CONFIG = {
  'user': os.getenv('DATABASE_USER'),
  'password': os.getenv('DATABASE_PASSWORD'),
  'host': os.getenv('DATABASE_HOST'),
  'database': os.getenv('DATABASE_NAME')
}

INFO = '''GET /items/ => Shows a list of all items
GET /items/?json=encodedjsondata => {"id":"num"} => Search for an item by id
GET /items/?json=encodedjsondata => {"name":"somename"} => Search for an item by name
POST /items/ => {"id":"num", "name":"somename", "qty":"num", "price":"num.num", "sid":"num"} => Add new item
PUT /items/ => {"id":"num", "qty":"num"} => Update the quantity of an item
DELETE /items/ => {"id":"num"} => Delete an item given its id'''

##########################################################
# ROUTES

# ---------------------------------------------------------
# Base

@APP.route("/")
def home_page():
    if request.method != "GET":
        return "Base route is GET only and it provides infomration about this API"

    # trick to display new lines in response
    return Markup(INFO.replace("\n", "<br>"))

# ---------------------------------------------------------
# Look up items

# TRY IT:
# curl -X GET http://34.105.39.147/items/
# curl -X GET http://34.105.39.147/items/?json={"id"%3A"3001"}

@APP.route('/items/', methods=['GET'])
def get_items():
    argsDict = None
    getArgs = request.args
    try: argsDict = json.loads(getArgs["json"])
    except: argsDict = {}
    stip_dict(argsDict)

    prepedStatementStr = ""
    valuesArr = []

    # list of all items
    if len(argsDict) == 0:
        prepedStatementStr = "SELECT * from items"

    # searching for items
    elif len(argsDict) == 1:
        if "id" not in argsDict and "name" not in argsDict:
            return "Items can only be searched using their name or id"
        if "id" in argsDict:
            if not is_digit(argsDict["id"]):
                return "Invalid id format."
            else: 
                prepedStatementStr = "SELECT * FROM items WHERE itemID=%s"
                valuesArr.append(argsDict['id'])
        elif "name" in argsDict:
            if not is_letters(argsDict["name"]):
                return "Invalid name format."
            else:
                prepedStatementStr = "SELECT * FROM items WHERE itemName=%s"
                valuesArr.append(argsDict['name'])
    
    else:
        return "Only 1 argument (id/name) for searching items expected"

    # RESPONSE
    response = talk_to_db(prepedStatementStr, valuesArr)
    if talk_to_db_success(response):
        return jsonify(response)
    else:
        print(response)
        return "internal error with the API"

# ---------------------------------------------------------
# Add new item

# TRY IT:
# curl -X POST http://34.105.39.147/items/ --header "Content-Type:application/json" --data '{"id":"6001", "name":"sixthousndone", "qty":"10", "price":"12.10", "sid":"50001"}'


@APP.route('/items/', methods=['POST'])
def add_new_item():
    argsDict = request.get_json()
    stip_dict(argsDict)

    result = arguments_validation(argsDict, ["id", "name", "qty", "price", "sid"])
    if result != "success": return result

    if not is_digit(argsDict["id"]): return "Invalid id format."

    response = talk_to_db("SELECT itemID FROM items WHERE itemID=%s", [argsDict["id"]])
    if not talk_to_db_success(response):
        print(response)
        return "error with the API"
    if len(response) > 0: return "Item already exists."

    if not is_letters(argsDict["name"]): return "Invalid name format."
    if not is_digit(argsDict["qty"]): return "Invalid qty format."
    if not is_number(argsDict["price"]): return "Invalid price format."
    if not is_digit(argsDict["sid"]): return "Invalid sid format."
    
    response = talk_to_db("SELECT supplierID FROM supplier WHERE supplierID=%s", [argsDict["sid"]])
    if not talk_to_db_success(response):
        print(response)
        return "internal error with the API"
    if len(response) == 0: return "Nonexistant sid"


    # return f"Add item {argsDict} using database operations"
    prepedStatementStr = "INSERT INTO items VALUES (%s, %s, %s, %s, %s)"
    valuesArr = [argsDict["id"], argsDict["name"], argsDict["qty"], argsDict["price"], argsDict["sid"]]
    response = talk_to_db(prepedStatementStr, valuesArr)

    if talk_to_db_success(response):
        return jsonify(success=True)
    else:
        print(response)
        return "internal error with the API"

# ---------------------------------------------------------
# Update item quanity

# TRY IT:
# curl -X PUT http://34.105.39.147/items/ --header "Content-Type:application/json" --data '{"id":"3001", "qty":"12"}'


@APP.route('/items/', methods=['PUT'])
def update_item_quantity():
    argsDict = request.get_json()
    stip_dict(argsDict)

    result = arguments_validation(argsDict, ["id", "qty"])
    if result != "success": return result

    if not is_digit(argsDict["id"]): return "Invalid id format."

    response = talk_to_db("SELECT itemID FROM items WHERE itemID=%s", [argsDict["id"]])
    if not talk_to_db_success(response):
        print(response)
        return "error with the API"
    if len(response) == 0: return "Nonexistant id"

    if not is_digit(argsDict["qty"]): return "Invalid qty format."

    response = talk_to_db("UPDATE items SET quantity=%s WHERE itemID=%s", [argsDict["qty"], argsDict["id"]])

    if talk_to_db_success(response):
        return jsonify(success=True)
    else:
        print(response)
        return "internal error with the API"

# ---------------------------------------------------------
# Delete item

# TRY IT:
# curl -X DELETE http://34.105.39.147/items/ --header "Content-Type:application/json" --data '{"id":"3008"}'


@APP.route('/items/', methods=['DELETE'])
def delete_item():
    argsDict = request.get_json()
    stip_dict(argsDict)

    result = arguments_validation(argsDict, ["id"])
    if result != "success": return result

    if not is_digit(argsDict["id"]): return "Invalid id format."

    response = talk_to_db("SELECT itemID FROM items WHERE itemID=%s", [argsDict["id"]])
    if not talk_to_db_success(response):
        print(response)
        return "error with the API"
    if len(response) == 0: return "Nonexistant id"

    response = talk_to_db("DELETE FROM items WHERE itemID=%s", [argsDict["id"]])

    if talk_to_db_success(response):
        return jsonify(success=True)
    else:
        print(response)
        return "internal error with the API"

##########################################################
# HELPERS

# ---------------------------------------------------------
# Validators

# check if the arguments (dict) is valid
def arguments_validation(dictRef, expectedKeysArr):
    if len(dictRef.keys()) < len(expectedKeysArr):
        return ["Not enough arguments provided."]
    elif len(dictRef.keys()) > len(expectedKeysArr):
        return ["Too many arguments provided."]
    elif sorted(dictRef.keys()) != sorted(expectedKeysArr):
        return ["Unexpected arguments present."]

    return "success"

def is_digit(str):
    return True if re.match(r"^\d+$", str) else False


def is_number(str):
    return True if re.match(r"^[0-9.]+$", str) else False


def is_letters(str):
    return True if re.match(r"^[a-zA-Z ]+$", str) else False

# ---------------------------------------------------------
# Database

# https://dev.mysql.com/doc/connector-python/en/connector-python-example-connecting.html


def connect_to_db():
    global DB

    try:
        if DB.is_connected(): return "success"
    except:
        pass

    try:
        DB = mysql.connector.connect(**DB_CONFIG)
        return "success"
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
            return "connect_to_db(): Something is wrong with your user name or password"
        elif err.errno == errorcode.ER_BAD_DB_ERROR:
            return "connect_to_db(): Database does not exist"
        else:
            return f"connect_to_db(): {err}"
    except Exception as e:
        return f"connect_to_db(): {e}"

# prepedStatementStr, replace any values with %s
# valuesArr, provide the values in order (if no values, empty list)
# ex. talk_to_db("SELECT * FROM items WHERE itemID=%s", [someId])
# returns:
    # SELECT: False (improper SQL command), an array of dictionaries (rows), or an empty array (if empty response)
    # Everything else: False (improper SQL command), True (executed successfully)


def talk_to_db(prepedStatementStr, valuesArr=None):
    connectToDbResult = connect_to_db()
    if connectToDbResult != "success": return connectToDbResult


    prepedStatementStr = prepedStatementStr.strip()
    if not valuesArr: valuesArr = []

    cursor = None

    try:
        cursor = DB.cursor()
        cursor.execute(prepedStatementStr, valuesArr)
    except Exception as e:
        return f"talk_to_db(): {e}"

    result = None

    if prepedStatementStr.lower().startswith("select"):
        result = {}
        resultKey = 0
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
            result[f"{resultKey}"] = rowDict
            resultKey += 1
    else:
        result = True
        # by default Connector/Python does not autocommit, it is important to call this method after every transaction that modifies data for tables
        DB.commit()

    cursor.close()
    return result

def talk_to_db_success(response):
    return True if type(response) is dict or response is True else False


##########################################################
# GENERIC HELPERS

def stip_dict(dictRef):
    for key, value in dictRef.items():
        dictRef[key] = value.strip()

##########################################################
# RUN

if __name__ == '__main__':
    APP.run(host='0.0.0.0', port=80)
