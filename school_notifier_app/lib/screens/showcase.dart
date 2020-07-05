import 'package:flutter/material.dart';

class Showcase extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Showcase'),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            RaisedButton(
              child: Text(
                'Login',
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () => Navigator.pushNamed(context, '/login'),
            ),
            RaisedButton(
              child: Text(
                'Settings',
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () => Navigator.pushNamed(context, '/settings'),
            ),
          ],
        ),
      ),
    );
  }
}