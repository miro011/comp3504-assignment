import 'dart:io';
import 'supplier.dart';
import 'package:cli_dart_app/resources.dart' as resources;

class SupplierList {
  List<Supplier> suppliers = [];

  SupplierList();

  SupplierList.fromString(String raw) {
    for (final line in raw.split('\n')) {
      Supplier s = Supplier.fromString(line);
      suppliers.add(s);
    }
  }

  SupplierList.fromFile(String filePath) {
    File f = File(filePath);
    var lines = f.readAsLinesSync();
    for (final line in lines) {
      suppliers.add(Supplier.fromString(line));
    }
  }

  SupplierList.fromResource(String resourceName) {
    var lines = resources.readAsLines(resourceName);
    for (final line in lines) {
      suppliers.add(Supplier.fromString(line));
    }
  }
}