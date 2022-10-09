import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/resources.dart' as resources;
import 'dart:math';

class ItemList {
  List<Item> items = [];

  ItemList();

  ItemList.addResourceItems(String path) {
    var rawLines = resources.readAsLines(path);

    for (final line in rawLines) {
      items.add(ItemList.itemWordParser(line));
    }
  }

  static Item itemWordParser(String singleLine) {
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

  getItemByIndex(int i) {
    return items[i];
  }

  getLength() {
    return items.length;
  }

  Item? getItemById(int id) {
    for (final item in items) {
      if (item.id == id) {
        return item;
      }
    }

    return null;
  }

  String toFileContents() {
    List<String> contentLines = [];

    for (final item in items) {
      contentLines.add(item.toFileContents());
    }

    String contents = contentLines.join('\n');
    return contents;
  }

  List<int> getIds() {
    List<int> ids = [];

    for (final item in items) {
      ids.add(item.id);
    }

    return ids;
  }

  //add item. Automatically generates item ID
  void addItem(name, quantity, price, supplierID) {
    //set ID of new item to ID of the last item + 1
    int itemId = items.last.id + 1;

    var newItem = Item(itemId, name, price, supplierID, quantity);

    items.add(newItem);
  }

  void remove(id, [quantity]) {
    if (quantity == null) {
      items.retainWhere((item) => item.id != id);
    } else if (quantity <= 0) {
      throw ArgumentError("Can't remove 0 or a negative number of an item");
    } else {
      Item? item = getItemById(id);
      if (item != null) {
        item.quantity -= min(item.quantity, quantity);
      }
    }
  }
}
