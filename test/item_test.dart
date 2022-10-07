import 'package:cli_dart_app/item.dart';
import 'package:test/test.dart';

void main() {
  group('Supplier.toFileContent', () {
    test('toFileContent valid string', () {
      Item i = Item(3000, 'Knock Bits', 12.67, 50015, 18);
      expect(i.toFileContents(), '3000;Knock Bits;18;12.67;50015');
    });
  });
  group('ItemList.==', () {
    test('Equal Objects', () {
      Item one = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, two);
    });
    test('Equal Objects w/o Optional Parameters', () {
      Item one = Item(3000, 'Knock Bits', 12.67, 50015, 18);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18);
      expect(one, two);
    });
    test('Unequal Objects by ID', () {
      Item one = Item(3001, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
    test('Unequal Objects by Name', () {
      Item one = Item(3000, 'Knock Bits1', 12.67, 50015, 18, 10, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
    test('Unequal Objects by Price', () {
      Item one = Item(3000, 'Knock Bits', 12.7, 50015, 18, 10, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
    test('Unequal Objects by SupplierID', () {
      Item one = Item(3000, 'Knock Bits', 12.67, 50016, 18, 10, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
    test('Unequal Objects by Quantity', () {
      Item one = Item(3000, 'Knock Bits', 12.67, 50015, 19, 10, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
    test('Unequal Objects by MinQuantity', () {
      Item one = Item(3000, 'Knock Bits', 12.67, 50015, 18, 11, 30);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
    test('Unequal Objects by DefaultOrderQuantity', () {
      Item one = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 31);
      Item two = Item(3000, 'Knock Bits', 12.67, 50015, 18, 10, 30);
      expect(one, isNot(equals(two)));
    });
  });
}
