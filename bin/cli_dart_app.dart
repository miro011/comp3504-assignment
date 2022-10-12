import 'dart:io';
import 'package:cli_dart_app/config.dart' as config;
import 'package:cli_dart_app/menu.dart' as menu;
import 'package:cli_dart_app/supplier_list.dart';
import 'package:cli_dart_app/resources.dart' as resources;
import 'package:cli_dart_app/item_list.dart';

//Creates a ItemList of Item objects from resource config files
ItemList ITEMS = ItemList.addResourceItems(config.items_resource_name);

//Creates a SupplierList of Supplier objects from resource config files
SupplierList SUPPLIERS =
SupplierList.fromResource(config.suppliers_resource_name);

// Holds the file for the orders.txt so we can append
File ORDERS_FILE = File(config.orders_resource_path);
// Holds the last order id, so we don't have to fetch it from the file
int LAST_ORDER_ID = 0;
String DATE = DateTime.now().toString().substring(0,10);
// holds the total cost of the items order during the run
double TOTAL_COST = 0;


void main(List<String> arguments) {

  // init last_order_id
  LAST_ORDER_ID = getLastOrderIdFromLastRun() + 1;

  //provide action menu
  bool running = true;

  try {
    while (running) {
      String answer = menu.mainMenu();
      switch (answer) {
        case menu.addItemKey:
          {
            menu.addItemMenu(ITEMS, SUPPLIERS);
            placeAutoOrder();
            break;
          }
        case menu.removeItemKey:
          {
            menu.removeItemMenu(ITEMS);
            placeAutoOrder();
            break;
          }
        case menu.searchItemKey:
          {
            menu.searchItemsMenu(ITEMS);
            break;
          }
        case menu.quitItemKey:
          {
            stdout.writeln('Quitting...');
            running = false;
            break;
          }
        default:
          {
            stderr.writeln(
                'Invalid selection returned for main menu. This only happens if'
                'something is implemented in the main menu but not in the '
                'switch statement after the main menu.');
          }
      }
    }
  } finally {
    resources.saveResourceWithBackupOnClobber(
        ITEMS, config.items_resource_name);

    // to mark the end of the run
    ORDERS_FILE.writeAsStringSync(
        "TOTAL COST FOR THIS RUN: $TOTAL_COST\n\n"
        "############################################################\n"
        "############################################################\n\n",
        mode: FileMode.append);
  }
}

void placeAutoOrder() {
  for (var itemObj in ITEMS.items) {
    if (itemObj.quantity < itemObj.minQuantity) {
      int numOrdered = itemObj.defaultOrderQuantity - itemObj.quantity;
      double cost = itemObj.price * numOrdered;
      TOTAL_COST += cost;
      itemObj.quantity = numOrdered;
      String orderMsg = "ORDER ID.: $LAST_ORDER_ID\n";
      orderMsg += "Date ordered: $DATE\n\n";
      orderMsg += "Item description: ${itemObj.name}\n";
      orderMsg += "Amount ordered: $numOrdered\n";
      orderMsg += "Supplier: ${getSupplierNameForItem(itemObj.supplierId)}\n\n";
      orderMsg += "Cost: $cost\n";
      orderMsg += "\n----------------------------------------------------------\n\n";
      ORDERS_FILE.writeAsStringSync(orderMsg, mode: FileMode.append);
      stdout.writeln("\n*** Auto-order placed for ${itemObj.name} ***\n");
      LAST_ORDER_ID++;
    }
  }
}

String getSupplierNameForItem(int supplierId) {
  for (var supplierObj in SUPPLIERS.suppliers) {
    if (supplierObj.id == supplierId) return supplierObj.name;
  }
  return "";
}

int getLastOrderIdFromLastRun() {
  List linesArr = ORDERS_FILE.readAsStringSync().split("\n");
  // go through the lines backwards
  try {
    for (var line in linesArr.reversed) {
      if (line.startsWith("ORDER ID")) {
        // split the line based on space and return the last item as int
        return int.parse(line.split(" ").last);
      }
    }
  } catch(e) {
    return 1; // if orders.txt is empty, corrupt etc.
  }
  return 1; // so function works
}
