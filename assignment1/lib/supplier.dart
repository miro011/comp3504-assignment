import 'package:cli_dart_app/config.dart' as config;


class Supplier {
  int id;
  String name;
  String address;
  String contactName;

  Supplier(
    this.id,
    this.name,
    this.address,
    this.contactName
  );

  @override
  String toString() {
    return 'Supplier('
      'id:$id'
      'name:$name'
      'address:$address'
      'contactName:$contactName'
    ')';
  }

  static Supplier fromString(String raw) {
    List<String> rawArr = raw.split(config.csv_separator);

    if (rawArr.length != 4) {
      throw FormatException('Number of elements in string must be 4, not ${rawArr.length}');
    }

    int id = int.parse(rawArr[0]);
    String name = rawArr[1];
    String address = rawArr[2];
    String contactName = rawArr[3];

    Supplier s = Supplier(id, name, address, contactName);
    return s;
  }
}