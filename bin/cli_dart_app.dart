import 'dart:io';
import 'package:cli_dart_app/menu.dart' as menu;


void main(List<String> arguments) {
  String answer = menu.mainMenu();
  switch(answer) {
    case menu.addItemKey: {
      var addedItem = menu.addItemMenu();
      stdout.writeln(addedItem);
      break;
    }
    case menu.removeItemKey: {
      stdout.writeln("Removing item...");
      break;
    }
    case menu.searchItemKey: {
      stdout.writeln("Searching for item...");
      break;
    }
    case menu.quitItemKey: {
      stdout.writeln("Quitting...");
      break;
    }
    default: {
      stderr.writeln("Invalid selection returned for main menu. This only happens if something is implemented in the main menu but not in the switch statement after the main menu.");
    }
  }
}
