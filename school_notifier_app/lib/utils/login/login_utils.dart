import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:school_notifier_app/settings/settings.dart';
import 'package:school_notifier_app/storage/storage.dart';
import 'package:school_notifier_app/utils/http/http_utils.dart';

class LoginUtils {
  static Future<String> login(String username, String password) async {
    final Map credentials = Map();
    credentials['module'] = Settings.APP_MODULE_NAME;
    credentials['username'] = username;
    credentials['password'] = password;

    try {
      http.Response response =
          await HttpUtils.doPost('/auth', credentials, false);

      if (response.statusCode == 200) {
        return response.body;
      }
    } on Exception catch (e) {
      debugPrint('Error to make login: $e');
    }

    return null;
  }

  static void saveToken(String token) {
    Storage.save('user.token', token);
  }

  static Future<String> readToken() async {
    return await Storage.read('user.token');
  }

  static bool validToken(String token) {
    return token != null && token.isNotEmpty;
  }

  static Future<bool> savedTokenIsValid() async {
    final String token = await readToken();
    return validToken(token);
  }

  static void resetToken() {
    Storage.remove('user.token');
  }
}