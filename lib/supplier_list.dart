import 'supplier.dart';

class SupplierList {
  List<Supplier> suppliers = [];

  SupplierList();

  SupplierList.fromString(String raw) {
    for (final line in raw.split('\n')) {
      Supplier s = Supplier.fromString(line);
      suppliers.add(s);
    }
  }
}