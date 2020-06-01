import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:school_notifier_web/models/user.dart';
import 'package:school_notifier_web/utils/http/http_utils.dart';

class ResponsibleDao {
  static Future<List<User>> find() async {
    final http.Response response =
        await HttpUtils.doPost('/responsible/my', null, true);

    if (response.statusCode == 200) {
      final List<User> responsibles = List();
      final List<dynamic> items = jsonDecode(response.body);

      for (var item in items) {
        User responsible = User.fromJson(item);
        responsibles.add(responsible);
      }

      return responsibles;
    }
    return null;
  }
}
