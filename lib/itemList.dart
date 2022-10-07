//import 'dart:js_util';

import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/resources.dart' as resources;

class ItemList {
  List<Item> items = [];

  ItemList();

  ItemList.addResourceItems(String path) {
    var rawLines = resources.readAsLines(path);

    for (final line in rawLines) {
      items.add(itemWordParser(line));
    }
  }

  Item itemWordParser(String singleLine) {
    List words = [];
    words = singleLine.split(";");

    var itemId = int.parse(words[0]);
    var name = words[1];
    var price = double.parse(words[3]);
    var supplierId = int.parse(words[4]);
    var quantity = int.parse(words[2]);

    var anItem = Item(itemId, name, price, supplierId, quantity);

    return anItem;
  }

  getItem(int i) {
    return items[i];
  }
}
