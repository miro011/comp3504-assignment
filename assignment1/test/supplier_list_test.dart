import 'package:cli_dart_app/supplier_list.dart';
import 'package:cli_dart_app/supplier.dart';
import 'package:test/test.dart';

void main() {
  group('Supplier.fromString', () {
    test('Valid From String, all elements', () {
      SupplierList s = SupplierList.fromString(
          '50001;GeniousBuilders;788 30th St., SE, Calgary;Fred\n'
          '50002;Pong Ping;749 Dufferin Blvd., SE, Calgary;Jacob\n'
          '50003;Wileys Inc.;26 40th St., SE, Calgary;BillyBob');
      expect(s.suppliers[0].id, 50001);
      expect(s.suppliers[0].name, 'GeniousBuilders');
      expect(s.suppliers[0].address, '788 30th St., SE, Calgary');
      expect(s.suppliers[0].contactName, 'Fred');
      expect(s.suppliers[1].id, 50002);
      expect(s.suppliers[1].name, 'Pong Ping');
      expect(s.suppliers[1].address, '749 Dufferin Blvd., SE, Calgary');
      expect(s.suppliers[1].contactName, 'Jacob');
      expect(s.suppliers[2].id, 50003);
      expect(s.suppliers[2].name, 'Wileys Inc.');
      expect(s.suppliers[2].address, '26 40th St., SE, Calgary');
      expect(s.suppliers[2].contactName, 'BillyBob');
    });
  });
  group('Supplier.fromFile', () {
    test('Valid From File, all elements', () {
      SupplierList s = SupplierList.fromFile('resources/suppliers_test.csv');
      expect(s.suppliers[0].id, 50001);
      expect(s.suppliers[0].name, 'GeniousBuilders');
      expect(s.suppliers[0].address, '788 30th St., SE, Calgary');
      expect(s.suppliers[0].contactName, 'Fred');
      expect(s.suppliers[1].id, 50002);
      expect(s.suppliers[1].name, 'Pong Ping');
      expect(s.suppliers[1].address, '749 Dufferin Blvd., SE, Calgary');
      expect(s.suppliers[1].contactName, 'Jacob');
      expect(s.suppliers[2].id, 50003);
      expect(s.suppliers[2].name, 'Wileys Inc.');
      expect(s.suppliers[2].address, '26 40th St., SE, Calgary');
      expect(s.suppliers[2].contactName, 'BillyBob');
    });
  });
}
