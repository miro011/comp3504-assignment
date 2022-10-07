import 'dart:io';
import 'package:cli_dart_app/config.dart' as config;
import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/menu.dart' as menu;
import 'package:cli_dart_app/supplier_list.dart';
import 'package:cli_dart_app/resources.dart' as resources;


void main(List<String> arguments) {
  List<String> lines = resources.readAsLines(config.items_resource_name);

  //delimeter for each setence
  List words = [];
  words = lines[1].split(";");

  var itemId = int.parse(words[0]);
  var name = words[1];
  var price = double.parse(words[3]);
  var supplierId = int.parse(words[4]);
  var quantity = int.parse(words[2]);

  var anItem = Item(itemId, name, price, supplierId, quantity);

  print(anItem);
  //create item from each line in the array
  //put items into itemList

  SupplierList suppliers = SupplierList.fromResource(config.suppliers_resource_name);

  //provide action menu
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
        break;
      }
    default:
      {
        stderr.writeln(
            'Invalid selection returned for main menu. This only happens if something is implemented in the main menu but not in the switch statement after the main menu.');
      }
  }
}
