import 'dart:io';
import 'package:cli_dart_app/config.dart' as config;
import 'package:cli_dart_app/menu.dart' as menu;
import 'package:cli_dart_app/supplier_list.dart';
import 'package:cli_dart_app/resources.dart' as resources;
import 'package:cli_dart_app/item_list.dart';

void main(List<String> arguments) {
  //Creates a ItemList of Item objects from resource config files
  ItemList items = ItemList.addResourceItems(config.items_resource_name);

  //Creates a SupplierList of Supplier objects from resource config files
  SupplierList suppliers =
      SupplierList.fromResource(config.suppliers_resource_name);

  // Holds the file for the orders
  File ordersFile = File(config.orders_resource_path);

  //provide action menu
  bool running = true;

  try {
    while (running) {
      String answer = menu.mainMenu();
      switch (answer) {
        case menu.addItemKey:
          {
            menu.addItemMenu(items, suppliers);
            placeAutoOrder(items, ordersFile);
            break;
          }
        case menu.removeItemKey:
          {
            menu.removeItemMenu(items);
            placeAutoOrder(items, ordersFile);
            break;
          }
        case menu.searchItemKey:
          {
            menu.searchItemsMenu(items);
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
        items, config.items_resource_name);
  }
}

void placeAutoOrder(ItemList items, File ordersFile) {
  for (var itemObj in items.items) {
    if (itemObj.quantity < itemObj.minQuantity) {
      int numOrdered = itemObj.defaultOrderQuantity - itemObj.quantity;
      itemObj.quantity = numOrdered;
      String orderMsg = "Item: ${itemObj.name}\nOty ordered: $numOrdered\n\n";
      ordersFile.writeAsStringSync(orderMsg, mode: FileMode.append);
      stdout.writeln("\n*** Auto-order placed for ${itemObj.name} ***\n");
    }
  }
}
