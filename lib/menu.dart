import 'dart:io';
import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/item_list.dart';
import 'package:cli_dart_app/supplier_list.dart';

const addItemKey = 'a';
const removeItemKey = 'r';
const searchItemKey = 's';
const quitItemKey = 'q';

String menuPrompt(String menu, dynamic validAnswers) {
  String? userInput;

  stdout.writeln(menu);
  while (1 == 1) {
    stdout.write('Answer: ');
    userInput = stdin.readLineSync();

    if (userInput == null) {
      stdout.writeln('STDIN was closed while we were waiting for input');
      continue;
    }

    userInput = userInput.trim();

    // runtimeType returns  the type of a variable
    if ("${validAnswers.runtimeType}" == "List<String>") {
      if (validAnswers.contains(userInput)) break;
    } else if ("${validAnswers.runtimeType}" == "_RegExp") {
      if (validAnswers.hasMatch(userInput)) break;
    } else {
      stderr
          .writeln('Menu prompt called using unsupported "ValidAnswers" type');
      return "";
    }

    stderr.writeln('Invalid input');
  }

  return userInput as String;
}

String mainMenu() {
  const validAnswers = [addItemKey, removeItemKey, searchItemKey, quitItemKey];
  const prompt = 'a: Add item\n'
      'r: Remove item\n'
      's: Search for item\n'
      'q: Quit\n';

  var answer = menuPrompt(prompt, validAnswers);

  return answer;
}

void addItemMenu(ItemList items, SupplierList suppliers) {
  String answer;
  String name;
  double price;
  int supplierID;
  int quantity;
  List<String> validSupplierID = [];

  //get list of supplier ID's
  suppliers.getSupplierIds().forEach((id) {
    validSupplierID.add(id.toString());
  });

  //Ask for info on new item
  name = menuPrompt('Item name', RegExp(r"^[a-zA-Z]+$"));

  answer = menuPrompt('Item price', RegExp(r"^[0-9]+\.?[0-9]*"));
  price = double.parse(answer);

  answer = menuPrompt('Supplier ID', validSupplierID);
  supplierID = int.parse(answer);

  answer = menuPrompt('Item quantity', RegExp(r"^[0-9]+$"));
  quantity = int.parse(answer);

  items.addItem(name, quantity, price, supplierID);
}

void removeItemMenu(ItemList items) {
  List<String> validAnswers = [];
  String answer;
  int id;
  int quantity;
  Item? item;

  items.getIds().forEach((id) {
    validAnswers.add(id.toString());
  });

  answer = menuPrompt('Item ID', validAnswers);
  id = int.parse(answer);

  item = items.getItemById(id);
  if (item == null) {
    stderr.writeln('User somehow entered an incorrect item ID (menuPrompt '
        'should have prevented this). Ignoring error.');
    return;
  }

  validAnswers = [];
  for (int i = 0; i <= item.quantity; i++) {
    validAnswers.add(i.toString());
  }
  validAnswers.add(''); // empty string means remove all

  answer = menuPrompt(
      'Quantity to remove out of ${item.quantity} (all)', validAnswers);
  if (answer == '') {
    items.remove(id, item.quantity);
  } else {
    quantity = int.parse(answer);
    items.remove(id, quantity);
  }
}

void searchItemsMenu(ItemList items) {
  // Get user inputs
  String searchTypeUserInput = menuPrompt(
      "\nSEARCH FOR:\nn: Name\ni: ID\nb: Back\n", RegExp(r"^n|i|b$"));

  String searchTextUserInput = "";
  if (searchTypeUserInput == "b") {
    return;
  } else if (searchTypeUserInput == "n") {
    searchTextUserInput = menuPrompt(
        "\nENTER FULL OR PARTIAL ITEM NAME:\n", RegExp(r"^[a-zA-Z]+$"));
    searchTextUserInput = searchTextUserInput.toLowerCase();
  } else if (searchTypeUserInput == "i") {
    searchTextUserInput =
        menuPrompt("\nENTER FULL OR PARTIAL ITEM ID:\n", RegExp(r"^[0-9]+$"));
  }

  // Find and display matched items
  for (var itemObj in items.items) {
    if ((searchTypeUserInput == "n" &&
            itemObj.name.toLowerCase().contains(searchTextUserInput)) ||
        (searchTypeUserInput == "i" &&
            itemObj.id.toString().contains(searchTextUserInput))) {
      print("-------------------------------------");
      print(itemObj.toString());
      print("-------------------------------------");
    }
  }
}
