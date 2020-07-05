import 'package:school_notifier_app/storage/storage.dart';

class Settings {
  static const String APP_MODULE_NAME = 'mobile';

  static Future<String> serverURL() async {
    var address = await serverAddress();
    return "${protocol()}://$address:${serverPort()}/${restApiPath()}";
  }

  static String protocol() {
    return 'http';
  }

  static Future<String> serverAddress() async {
    var address = await Storage.read('ip');
    if (address != null && address.isNotEmpty) {
      return address;
    }
    return '10.0.2.2';
  }

  static String serverPort() {
    return '8080';
  }

  static String restApiPath() {
    return 'school_notifier';
  }
}
