import 'dart:io';
import 'package:cli_dart_app/item.dart';
import 'package:cli_dart_app/item_list.dart';

const addItemKey = 'a';
const removeItemKey = 'r';
const searchItemKey = 's';
const quitItemKey = 'q';

String menuPrompt(String menu, List<String> validAnswers) {
  String? userInput;

  stdout.writeln(menu);
  while (!validAnswers.contains(userInput)) {
    stdout.write('Answer: ');
    userInput = stdin.readLineSync();

    if (userInput == null) {
      stdout.writeln('STDIN was closed while we were waiting for input');
      continue;
    }

    if (!validAnswers.contains(userInput)) {
      stdout.writeln('Invalid input');
      continue;
    }
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

Item addItemMenu(ItemList items) {
  var a = Item(10, 'Test item', 10, 10, 10);
  return a;
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

  answer = menuPrompt('Quantity to remove out of ${item.quantity} (all)', validAnswers);
  if (answer == '') {
    items.remove(id);
  } else {
    quantity = int.parse(answer);
    items.remove(id, quantity);
  }
}
