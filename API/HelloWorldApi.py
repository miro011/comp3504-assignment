import flask
from flask import jsonify, request

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/hello', methods=['GET'])
def home():

    if (request.method == 'GET'):
        data = {"data": "Hello World"}
        return jsonify(data)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)
