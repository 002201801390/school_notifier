import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';
import 'package:school_notifier_web/dao/student_dao.dart';
import 'package:school_notifier_web/models/user.dart';

class StudentAdd extends StatelessWidget {
  final _nameController = TextEditingController();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Adicionar aluno'),
      ),
      body: Form(
        key: _formKey,
        child: Column(
          children: [
            TextComponents.textField(
              'Nome',
              controller: _nameController,
              validator: _nameValidator,
            ),
            TextComponents.textField(
              'Usuário',
              controller: _usernameController,
              validator: _usernameValidator,
            ),
            TextComponents.textField(
              'Senha',
              controller: _passwordController,
              validator: _passwordValidator,
              obscure: true,
            ),
            RaisedButton(
              child: Text('Cadastrar'),
              onPressed: () => _btnSavePressed(),
            ),
          ],
        ),
      ),
    );
  }

  String _nameValidator(String name) {
    if (name == null || name.isEmpty) {
      return "Nome vazio";
    }
    return null;
  }

  String _usernameValidator(String username) {
    if (username == null || username.isEmpty) {
      return "Usuario vazio";
    }
    return null;
  }

  String _passwordValidator(String name) {
    if (name == null || name.isEmpty) {
      return "Senha vazia";
    }
    return null;
  }

  _btnSavePressed() {
    if (!_formKey.currentState.validate()) {
      return;
    }

    StudentDao.save(_studentData());
  }

  _studentData() {
    return User(
      name: _nameController.text,
      username: _usernameController.text,
      password: _passwordController.text,
    );
  }
}
