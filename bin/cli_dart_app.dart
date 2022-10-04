import 'package:cli_dart_app/cli_dart_app.dart' as cli_dart_app;

void main(List<String> arguments) {
  var count = 0;
  if (count < 10) {
    print('Hello world: ${cli_dart_app.calculate()}!');
  }
}
