import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:school_notifier_app/utils/login/login_utils.dart';

class DashboardMenu extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          DrawerHeader(
            child: Text('Side Menu'),
          ),
          ListTile(
            leading: Icon(
              Icons.exit_to_app,
              color: Colors.red,
            ),
            title: Text(
              'Logout',
              style: TextStyle(color: Colors.red),
            ),
            onTap: () {
              LoginUtils.resetToken();

              Navigator.popUntil(context, (route) {
                return '/' == route.settings.name;
              });
            },
          )
        ],
      ),
    );
  }
}
