import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:school_notifier_web/models/user.dart';
import 'package:school_notifier_web/utils/http/http_utils.dart';

class StudentDao {
  static Future<List<User>> find() async {
    final http.Response response =
        await HttpUtils.doPost('/student/my', null, true);

    if (response.statusCode == 200) {
      final List<User> students = List();
      final List<dynamic> items = jsonDecode(response.body);

      for (var item in items) {
        User student = User.fromJson(item);
        students.add(student);
      }

      return students;
    }
    return null;
  }

  static Future<bool> save(User studentData) async {

    final studentJson = jsonEncode(studentData.toJson());
    final data = Map();
    data['data'] = studentJson;

    final response =
        await HttpUtils.doPost('/student/insert', studentJson, true);

    return response.statusCode == 200;
  }
}
