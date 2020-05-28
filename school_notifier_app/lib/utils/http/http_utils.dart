import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:school_notifier_app/settings/settings.dart';
import 'package:school_notifier_app/storage/storage.dart';

class HttpUtils {
  static Future<http.Response> doPost(
      String path, Map body, bool useToken) async {
    Map headers;
    if (useToken) {
      final String token = await Storage.read('user.token');

      headers = Map();
      headers['Authorization'] = 'Token $token';
    }

    return http.post(
      '${Settings.serverURL()}$path',
      headers: headers,
      body: body,
      encoding: Encoding.getByName('UTF-8'),
    );
  }
}
