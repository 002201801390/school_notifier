import 'package:flutter/material.dart';
import 'package:school_notifier_app/models/user.dart';

class Student extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final User student = ModalRoute.of(context).settings.arguments;
    return Scaffold(
      appBar: AppBar(
        title: Text('Aluno'),
      ),
      body: _buildStudentBody(student),
    );
  }

  _buildStudentBody(User student) {
    return Text(student.name);
  }
}
