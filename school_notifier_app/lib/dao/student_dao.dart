import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:school_notifier_app/models/user.dart';
import 'package:school_notifier_app/utils/http/http_utils.dart';

class StudentDao {
  static Future<List<User>> findStudents() async {
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
}
