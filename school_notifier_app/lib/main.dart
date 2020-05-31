import 'package:flutter/material.dart';
import 'package:school_notifier_app/screens/dashboard/dashboard.dart';
import 'package:school_notifier_app/screens/dashboard/student/student.dart';
import 'package:school_notifier_app/screens/login.dart';
import 'package:school_notifier_app/screens/showcase.dart';
import 'package:school_notifier_app/utils/login/login_utils.dart';

String _initialRoute;

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  _initialRoute = await _chooseInitialRoute();

  runApp(SchoolNotifierApp());
}

class SchoolNotifierApp extends StatelessWidget {
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
      '/login': (context) => Login(),
      '/dashboard': (context) => Dashboard(),
      '/dashboard/student': (context) => Student(),
    };
  }
}

Future<String> _chooseInitialRoute() async {
  if (await LoginUtils.savedTokenIsValid()) {
    return '/dashboard';
  }
  return '/';
}
