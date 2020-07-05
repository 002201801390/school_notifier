import 'dart:convert';

import 'package:school_notifier_app/models/class.dart';

import 'package:http/http.dart' as http;
import 'package:school_notifier_app/utils/http/http_utils.dart';

class ClassDao {
  static Future<List<Class>> find() async {
    final http.Response response =
        await HttpUtils.doGet('/class/all', true);

    if (response.statusCode == 200) {
      final List<Class> classes = List();
      final List<dynamic> items = jsonDecode(response.body);

      for (var item in items) {
        Class clazz = Class.fromJson(item);
        classes.add(clazz);
      }

      return classes;
    }
    return null;
  }
}
