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
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextComponents.textField(
                label: 'Nome',
                controller: _nameController,
                validator: _nameValidator,
              ),
              TextComponents.textField(
                label: 'Usuário',
                controller: _usernameController,
                validator: _usernameValidator,
              ),
              TextComponents.textField(
                label: 'Senha',
                controller: _passwordController,
                validator: _passwordValidator,
                obscure: true,
              ),
              Spacer(flex: 1),
              RaisedButton(
                child: Text('Cadastrar'),
                onPressed: () => _btnSavePressed(context),
              ),
              Spacer(flex: 10),
            ],
          ),
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

  _btnSavePressed(BuildContext context) async {
    if (!_formKey.currentState.validate()) {
      return;
    }

    final success = await StudentDao.save(_studentData());
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao salvar aluno'),
      );
      return;
    }

    Navigator.pop(context);
  }

  _alertDialog(BuildContext context, String message) {
    return AlertDialog(
      title: Text(message),
      actions: <Widget>[
        FlatButton(
          child: Text('Ok'),
          onPressed: () => Navigator.pop(context),
        ),
      ],
    );
  }

  _studentData() {
    return User(
      name: _nameController.text,
      username: _usernameController.text,
      password: _passwordController.text,
    );
  }
}
