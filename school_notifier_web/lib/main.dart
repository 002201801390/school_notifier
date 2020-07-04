import 'package:flutter/material.dart';
import 'package:school_notifier_web/screens/dashboard.dart';
import 'package:school_notifier_web/screens/employee/employee.dart';
import 'package:school_notifier_web/screens/employee/employee_list.dart';
import 'package:school_notifier_web/screens/login.dart';
import 'package:school_notifier_web/screens/responsible/responsible.dart';
import 'package:school_notifier_web/screens/responsible/responsible_add.dart';
import 'package:school_notifier_web/screens/responsible/responsible_list.dart';
import 'package:school_notifier_web/screens/settings.dart';
import 'package:school_notifier_web/screens/showcase.dart';
import 'package:school_notifier_web/screens/student/student.dart';
import 'package:school_notifier_web/screens/student/student_add.dart';
import 'package:school_notifier_web/screens/student/student_list.dart';
import 'package:school_notifier_web/utils/login/login_utils.dart';
import 'package:shared_preferences/shared_preferences.dart';

String _initialRoute;

void main() async {
  // ignore: invalid_use_of_visible_for_testing_member
  SharedPreferences.setMockInitialValues({});

  WidgetsFlutterBinding.ensureInitialized();

  _initialRoute = await _chooseInitialRoute();

  runApp(SchoolNotifierWeb());
}

class SchoolNotifierWeb extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'School Notifier',
      theme: ThemeData.dark(),
      routes: _routes(context),
      initialRoute: _initialRoute,
    );
  }

  _routes(BuildContext context) {
    return {
      '/': (context) => Showcase(),
      '/settings': (context) => Settings(),
      '/login': (context) => Login(),
      '/dashboard': (context) => Dashboard(),
      '/dashboard/employee': (context) => Employee(),
      '/dashboard/employee_list': (context) => EmployeeList(),
      '/dashboard/employee_list/employee_add': (context) => ResponsibleAdd(),
      '/dashboard/responsible': (context) => Responsible(),
      '/dashboard/responsible_list': (context) => ResponsibleList(),
      '/dashboard/responsible_list/responsible_add': (context) => ResponsibleAdd(),
      '/dashboard/student': (context) => Student(),
      '/dashboard/student_list': (context) => StudentList(),
      '/dashboard/student_list/student_add': (context) => StudentAdd(),
    };
  }
}

Future<String> _chooseInitialRoute() async {
  if (await LoginUtils.savedTokenIsValid()) {
    return '/dashboard';
  }
  return '/';
}
