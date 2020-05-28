import 'package:shared_preferences/shared_preferences.dart';

class Storage {
  static save(final String key, final String value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(key, value);
  }

  static Future<String> read(final String key) async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(key);
  }

  static remove(final String key) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(key);
  }
}
