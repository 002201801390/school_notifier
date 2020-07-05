import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:school_notifier_app/models/user.dart';
import 'package:school_notifier_app/settings/settings.dart';
import 'package:school_notifier_app/storage/storage.dart';
import 'package:school_notifier_app/utils/http/http_utils.dart';

class LoginUtils {
  static Future<int> login(String username, String password) async {
    final Map credentials = Map();
    credentials['module'] = Settings.APP_MODULE_NAME;
    credentials['username'] = username;
    credentials['password'] = password;

    try {
      http.Response response =
          await HttpUtils.doPost('/auth', credentials, false);

      if (response.statusCode == 200) {
        String token = response.body;

        saveToken(token);

        _updateUserCredentials();
      }
      return response.statusCode;

    } on Exception catch (e) {
      debugPrint('Error to make login: $e');
    }

    return 404;
  }

  static void saveToken(String token) async {
    await Storage.save('user.token', token);
  }

  static Future<String> readToken() async {
    return await Storage.read('user.token');
  }

  static Future<bool> validToken(String token) async {
    if (token == null || token.isEmpty) {
      return false;
    }

    http.Response response = await HttpUtils.doPost('/auth/check', null, true);
    return response.statusCode == 200;
  }

  static Future<bool> savedTokenIsValid() async {
    final String token = await readToken();
    return validToken(token);
  }

  static void resetToken() async {
    await Storage.remove('user.token');
  }

  static void _updateUserCredentials() async {
    if (await savedTokenIsValid()) {
      http.Response response =
          await HttpUtils.doPost('/credentials', null, true);

      if (response.statusCode == 200) {
        await Storage.save('user.credentials', response.body);
      }
    } else {
      await Storage.remove('user.credentials');
    }
  }

  static Future<User> loggedUserCredentials() async {
    if (!await savedTokenIsValid()) {
      return null;
    }

    final String userJson = await Storage.read('user.credentials');
    if (userJson == null || userJson.isEmpty) {
      return null;
    }

    final User user = User.fromJson(jsonDecode(userJson));
    return user;
  }
}
