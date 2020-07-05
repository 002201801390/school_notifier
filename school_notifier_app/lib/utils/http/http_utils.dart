import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:school_notifier_app/settings/settings.dart';
import 'package:school_notifier_app/storage/storage.dart';

class HttpUtils {
  static Future<http.Response> doPost(
      String path, body, bool useToken) async {
    return http.post(
      '${await Settings.serverURL()}$path',
      headers: await _headers(useToken),
      body: body,
      encoding: Encoding.getByName('UTF-8'),
    );
  }

  static Future<http.Response> doGet(String path, bool useToken) async {
    return http.get(
      '${await Settings.serverURL()}$path',
      headers: await _headers(useToken),
    );
  }

  static Future<Map<String, String>> _headers(bool useToken) async {
    Map<String, String> headers;
    if (useToken) {
      final String token = await Storage.read('user.token');

      headers = Map();
      headers['Authorization'] = 'Token $token';
      headers['Module'] = 'Module ${Settings.APP_MODULE_NAME}';
    }
    return headers;
  }
}
