import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:school_notifier_app/models/user.dart';
import 'package:school_notifier_app/utils/http/http_utils.dart';

class EmployeeDao {
  static Future<List<User>> find() async {
    final http.Response response = await HttpUtils.doGet('/employee/all', true);

    if (response.statusCode == 200) {
      final List<User> employees = List();
      final List<dynamic> items = jsonDecode(response.body);

      for (var item in items) {
        User employee = User.fromJson(item);
        employees.add(employee);
      }

      return employees;
    }
    return null;
  }
}
