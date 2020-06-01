import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';
import 'package:school_notifier_web/models/user.dart';

class Student extends StatelessWidget {
  final GlobalKey<FormState> _formKey = GlobalKey();

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
        _studentPofilePic(),
        Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  TextComponents.prefixedTextField('Nome:', width: 300),
                ],
              ),
            ),
          ),
        ),
      ],
    );
  }

  Row _studentPofilePic() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Center(
          child: Icon(
            Icons.person,
            size: 256,
          ),
        ),
      ],
    );
  }
}
