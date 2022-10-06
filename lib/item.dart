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
}
