import 'package:cli_dart_app/config.dart' as config;


class Item {
  int id;
  String name;
  int quantity;
  int minQuantity;
  int defaultOrderQuantity;
  double price;
  int supplierId;

  Item(this.id, this.name, this.price, this.supplierId, this.quantity,
      [this.minQuantity = 10, this.defaultOrderQuantity = 30]);

  @override
  String toString() {
    return '{'
        'id: $id, '
        'name: $name, '
        'price: $price, '
        'supplier ID: $supplierId, '
        'quantity: $quantity'
        '}';
  }

  String toFileContents() {
    List<String> strArr = [
      id.toString(),
      name,
      quantity.toString(),
      price.toStringAsFixed(2),
      supplierId.toString()
    ];
    return strArr.join(config.csv_separator);
  }

  @override
  // Had to implement this as the default one checks if two objects reference
  // the same thing in memory, not if their attributes are equal.
  bool operator ==(var other) {
    return (
      other is Item &&
      other.id == id &&
      other.name == name &&
      other.quantity == quantity &&
      other.price == price &&
      other.supplierId == supplierId &&
      other.minQuantity == minQuantity &&
      other.defaultOrderQuantity == defaultOrderQuantity
    );
  }

  @override
  int get hashCode => super.hashCode;

}
