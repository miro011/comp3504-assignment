import 'package:cli_dart_app/item_list.dart';
import 'package:cli_dart_app/item.dart';
import 'package:test/test.dart';


ItemList generateItemList() {
  ItemList s = ItemList();
  s.items.add(ItemList.itemWordParser('3000;Knock Bits;18;12.67;50015'));
  s.items.add(ItemList.itemWordParser('3001;Widgets;10;35.50;50004'));
  s.items.add(ItemList.itemWordParser('3002;Grommets;20;23.45;50001'));
  return s;
}


void main() {
  group('ItemList.toFileContents', () {
    test('Valid From String, all elements', () {
      ItemList s = generateItemList();

      expect(s.toFileContents(),
          '3000;Knock Bits;18;12.67;50015\n'
          '3001;Widgets;10;35.50;50004\n'
          '3002;Grommets;20;23.45;50001');
    });
  });
  group('ItemList.getIds', () {
    test('Three items', () {
      ItemList s = generateItemList();

      expect(s.getIds(), [3000, 3001, 3002]);
    });
  });
  group('ItemList.remove', () {
    test('Remove valid item', () {
      ItemList items = generateItemList();

      items.remove(3000);
      expect(items.toFileContents(),
          '3001;Widgets;10;35.50;50004\n'
          '3002;Grommets;20;23.45;50001');
    });
    test('Remove invalid item', () {
      ItemList items = generateItemList();

      items.remove(9000);
      expect(items.toFileContents(),
          '3000;Knock Bits;18;12.67;50015\n'
          '3001;Widgets;10;35.50;50004\n'
          '3002;Grommets;20;23.45;50001');
    });
  });
  group('ItemList.getItemById', () {
    test('Remove existing item', () {
      ItemList items = generateItemList();

      Item? item = items.getItemById(3000);
      expect(item, ItemList.itemWordParser('3000;Knock Bits;18;12.67;50015'));
    });
    test('Remove non-existing item', () {
      ItemList items = generateItemList();

      Item? item = items.getItemById(9000);
      expect(item, null);
    });
  });
}
