import 'package:cli_dart_app/item_list.dart';
import 'package:test/test.dart';

void main() {
  group('ItemList.toFileContents', () {
    test('Valid From String, all elements', () {
      ItemList s = ItemList();
      s.items.add(ItemList.itemWordParser('3000;Knock Bits;18;12.67;50015'));
      s.items.add(ItemList.itemWordParser('3001;Widgets;10;35.50;50004'));
      s.items.add(ItemList.itemWordParser('3002;Grommets;20;23.45;50001'));
      expect(s.toFileContents(),
          '3000;Knock Bits;18;12.67;50015\n'
          '3001;Widgets;10;35.50;50004\n'
          '3002;Grommets;20;23.45;50001');
    });
  });
}
