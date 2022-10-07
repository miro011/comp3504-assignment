import 'dart:io';
import 'package:cli_dart_app/config.dart' as config;
import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/menu.dart' as menu;
import 'package:cli_dart_app/supplier_list.dart';
import 'package:cli_dart_app/resources.dart' as resources;
import 'package:cli_dart_app/item_list.dart';

void main(List<String> arguments) {
  //Creates a ItemList of Item objects from resource config files
  ItemList items = ItemList.addResourceItems(config.items_resource_name);

  //print(items.getItem(1).name);

  //Creates a SupplierList of Supplioer objects from resource config files
  SupplierList suppliers =
      SupplierList.fromResource(config.suppliers_resource_name);

  //provide action menu
  bool running = true;

  try {
    while (running) {
      String answer = menu.mainMenu();
      switch (answer) {
        case menu.addItemKey:
          {
            var addedItem = menu.addItemMenu();
            stdout.writeln(addedItem);
            break;
          }
        case menu.removeItemKey:
          {
            stdout.writeln('Removing item...');
            break;
          }
        case menu.searchItemKey:
          {
            stdout.writeln('Searching for item...');
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
