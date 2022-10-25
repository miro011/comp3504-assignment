import 'dart:io';
import 'package:cli_dart_app/config.dart' as config;
import 'package:cli_dart_app/item_list.dart';
import 'package:cli_dart_app/supplier_list.dart';

class Aoh {
  // late is a way to disable the null-safety feature of dart
  // basically telling it that "at a later point of time you will initialize it
    // and it won't be null"
  late ItemList itemsListRef;
  late SupplierList suppliersListRef;
  late File ordersFile;
  late int lastOrderId;
  late double totalCost;
  late String date;

  // Constructor
  Aoh(ItemList itemsList, SupplierList supplierList) {
    this.itemsListRef = itemsList;
    this.suppliersListRef = supplierList;
    this.ordersFile = File(config.orders_resource_path);
    this.lastOrderId = getLastOrderIdFromFile() + 1;
    this.totalCost = 0;
    this.date = DateTime.now().toString().substring(0,10);
  }

  // Goes through orders.txt line by line and captures the last order id
  int getLastOrderIdFromFile() {
    List linesArr = this.ordersFile.readAsStringSync().split("\n");
    // go through the lines backwards for efficiency
    try {
      for (var line in linesArr.reversed) {
        if (line.startsWith("ORDER ID")) {
          // split the line based on space and return the last item as int
          return int.parse(line.split(" ").last);
        }
      }
    } catch(e) {
      return 0; // if orders.txt is empty, corrupt etc.
    }
    return 0; // so function works
  }

  // Given the id of a supplier (taken from an item)
  // Loops through the suppliers list and returns the name
  String getSupplierName(int supplierId) {
    for (var supplierObj in this.suppliersListRef.suppliers) {
      if (supplierObj.id == supplierId) return supplierObj.name;
    }
    return "";
  }

  void run() {
    for (var itemObj in this.itemsListRef.items) {
      if (itemObj.quantity < itemObj.minQuantity) {
        int numOrdered = itemObj.defaultOrderQuantity - itemObj.quantity;
        double cost = itemObj.price * numOrdered;
        this.totalCost += cost;

        itemObj.quantity = itemObj.quantity + numOrdered;

        String orderMsg = "ORDER ID.: ${this.lastOrderId}\n";
        orderMsg += "Date ordered: ${this.date}\n\n";
        orderMsg += "Item description: ${itemObj.name}\n";
        orderMsg += "Amount ordered: $numOrdered\n";
        orderMsg += "Supplier: ${getSupplierName(itemObj.supplierId)}\n\n";
        orderMsg += "Cost: $cost\n";
        orderMsg += "\n----------------------------------------------------------\n\n";

        this.ordersFile.writeAsStringSync(orderMsg, mode: FileMode.append);
        stdout.writeln("\n*** Auto-order placed for ${itemObj.name} ***\n");
        this.lastOrderId++;
      }
    }
  }

  // When the program is quit, this is run to mark the end and total cost
  void close() {
    this.ordersFile.writeAsStringSync(
        "TOTAL COST FOR THIS RUN: ${this.totalCost}\n\n"
            "##########################################################\n\n",
        mode: FileMode.append);
  }
}
