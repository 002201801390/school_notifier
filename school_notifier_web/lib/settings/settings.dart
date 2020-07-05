import 'package:school_notifier_web/storage/storage.dart';

class Settings {
  static const String APP_MODULE_NAME = 'web';

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
    return '127.0.0.1';
  }

  static String serverPort() {
    return '8080';
  }

  static String restApiPath() {
    return 'school_notifier';
  }
}
