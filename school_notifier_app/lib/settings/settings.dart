class Settings {
  static const String APP_MODULE_NAME = 'mobile';

  static String serverURL() {
    return "${protocol()}://${serverAddress()}:${serverPort()}/${restApiPath()}";
  }

  static String protocol() {
    return 'http';
  }

  static String serverAddress() {
    return '10.0.2.2';
  }

  static String serverPort() {
    return '8080';
  }

  static String restApiPath() {
    return 'school_notifier';
  }
}
