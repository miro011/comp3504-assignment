import flask


app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/', methods=['GET'])
def home():
    return "<h1><p>Hello World</p></h1>"


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)
