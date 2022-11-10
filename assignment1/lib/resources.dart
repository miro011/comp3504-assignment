import 'dart:io';
import 'package:cli_dart_app/config.dart' as config;
import 'package:path/path.dart' as path;

String? resourcesPath;

void findResourcesDir() {
  Directory? resourcesDir;

  if (resourcesPath == null) {
    for (final dir in config.resources_dirs) {
      resourcesDir = Directory(dir);
      if (resourcesDir.existsSync()) {
        resourcesPath = dir;
        break;
      }
    }
    if (resourcesPath == null) {
      throw FileSystemException(
          "Couldn't find any of the hard-coded resource directories");
    }
  }
}

String readAsString(String resourceName) {
  findResourcesDir();

  File f = File(path.join(resourcesPath as String, resourceName));

  String contents = f.readAsStringSync();

  return contents;
}

List<String> readAsLines(String resourceName) {
  findResourcesDir();

  File f = File(path.join(resourcesPath as String, resourceName));

  List<String> contents = f.readAsLinesSync();

  return contents;
}

void saveResourceWithBackupOnClobber(var data, String resourceName) {
  findResourcesDir();

  File output = File(path.join(resourcesPath as String, resourceName));
  if (output.existsSync()) {
    output
        .renameSync(path.join(resourcesPath as String, resourceName + '.bak'));
  }

  output.writeAsStringSync(data.toFileContents());
}
