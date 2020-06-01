import 'package:flutter/material.dart';
import 'package:school_notifier_web/models/user.dart';

class Employee extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final employee = ModalRoute.of(context).settings.arguments;

    return Scaffold(
      appBar: AppBar(
        title: Text('Funcionario'),
        centerTitle: true,
      ),
      body: _body(employee),
    );
  }

  _body(User employee) {
    return Column(
      children: [
        Text('Nome: ${employee.name}'),
      ],
    );
  }
}
