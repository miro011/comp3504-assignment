import 'package:cli_dart_app/supplier.dart';
import 'package:test/test.dart';

void main() {
  group('Supplier.fromString', () {
    test('Valid From String, id', () {
      Supplier s = Supplier.fromString(
          '50001;GeniousBuilders;788 30th St., SE, Calgary;Fred');
      expect(s.id, 50001);
    });
    test('Valid From String, name', () {
      Supplier s = Supplier.fromString(
          '50001;GeniousBuilders;788 30th St., SE, Calgary;Fred');
      expect(s.name, 'GeniousBuilders');
    });
    test('Valid From String, Address', () {
      Supplier s = Supplier.fromString(
          '50001;GeniousBuilders;788 30th St., SE, Calgary;Fred');
      expect(s.address, '788 30th St., SE, Calgary');
    });
    test('Valid From String, contactName', () {
      Supplier s = Supplier.fromString(
          '50001;GeniousBuilders;788 30th St., SE, Calgary;Fred');
      expect(s.contactName, 'Fred');
    });
  });

  group('Supplier.Supplier', () {
    test('Valid From String - id', () {
      Supplier s = Supplier(
          50001, 'GeniousBuilders', '788 30th St., SE, Calgary', 'Fred');
      expect(s.id, 50001);
    });
    test('Valid From String - name', () {
      Supplier s = Supplier(
          50001, 'GeniousBuilders', '788 30th St., SE, Calgary', 'Fred');
      expect(s.name, 'GeniousBuilders');
    });
    test('Valid From String - Address', () {
      Supplier s = Supplier(
          50001, 'GeniousBuilders', '788 30th St., SE, Calgary', 'Fred');
      expect(s.address, '788 30th St., SE, Calgary');
    });
    test('Valid From String - contactName', () {
      Supplier s = Supplier(
          50001, 'GeniousBuilders', '788 30th St., SE, Calgary', 'Fred');
      expect(s.contactName, 'Fred');
    });
  });
}
