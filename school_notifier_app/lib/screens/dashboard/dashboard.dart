import 'package:flutter/material.dart';
import 'package:school_notifier_app/screens/dashboard/dashboard_menu.dart';

class Dashboard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Scaffold(
        appBar: AppBar(
          title: Text('Dashboard'),
          automaticallyImplyLeading: false,
          centerTitle: true,
        ),
        drawer: DashboardMenu(),
      ),
    );
  }
}
