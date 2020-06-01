import 'package:flutter/material.dart';
import 'package:school_notifier_web/models/user.dart';

class Responsible extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final responsible = ModalRoute.of(context).settings.arguments;

    return Scaffold(
      appBar: AppBar(
        title: Text('Pai/Responsável'),
        centerTitle: true,
      ),
      body: _body(responsible),
    );
  }

  _body(User responsible) {
    return Column(
      children: [
        Text('Nome: ${responsible.name}'),
      ],
    );
  }
}
