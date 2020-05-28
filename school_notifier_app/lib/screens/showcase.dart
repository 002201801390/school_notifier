import 'package:flutter/material.dart';

class Showcase extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Showcase'),
      ),
      body: Row(
        children: <Widget>[
          RaisedButton(
            child: Text('Login'),
            onPressed: () => Navigator.pushNamed(context, '/login'),
          ),
        ],
      ),
    );
  }
}
