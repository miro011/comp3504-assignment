class AddedItem {
  int id;
  String name;
  int quantity;
  int minQuantity;
  int defaultOrderQuantity;

  AddedItem(
    this.id,
    this.name,
    this.quantity,
    [
      this.minQuantity=10,
      this.defaultOrderQuantity=30
    ]
  );

@override
String toString() {
    return '{'
      'id: $id, '
      'name: $name, '
      'quantity: $quantity'
      '}';
  }
}