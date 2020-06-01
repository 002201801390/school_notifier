import 'package:flutter/material.dart';
import 'package:school_notifier_web/models/user.dart';

class Student extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final student = ModalRoute.of(context).settings.arguments;

    return Scaffold(
      appBar: AppBar(
        title: Text('Aluno'),
        centerTitle: true,
      ),
      body: _body(student),
    );
  }

  _body(User student) {
    return Column(
      children: [
        Text('Nome: ${student.name}'),
      ],
    );
  }
}
