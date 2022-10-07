import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/resources.dart' as resources;

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

  getItem(int i) {
    return items[i];
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


  void remove(id) {
    items.retainWhere((item) => item.id != id);
  }
}
