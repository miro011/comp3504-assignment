import 'package:cli_dart_app/item.dart';
import 'package:test/test.dart';

void main() {
  group('Supplier.toFileContent', () {
    test('toFileContent valid string', () {
      Item i = Item(
          3000, 'Knock Bits', 12.67, 50015, 18);
      expect(i.toFileContents(),
          '3000;Knock Bits;18;12.67;50015');
    });
  });
}