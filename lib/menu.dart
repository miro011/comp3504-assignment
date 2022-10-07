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
      stderr.writeln('Received no input');
      continue;
    }

    if (!validAnswers.contains(userInput)) {
      stderr.writeAll(["Received invalid user input: '", userInput, "'\n"]);
      continue;
    }

    stderr.writeln('Received valid user input');
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
  const prompt = 'Item ID';

  items.getIds().forEach((id) {
     validAnswers.add(id.toString());
  });

  String answer = menuPrompt(prompt, validAnswers);
  items.remove(int.parse(answer));
}