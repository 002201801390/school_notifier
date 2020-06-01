import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';

class EmployeeAdd extends StatelessWidget {
  final _nameController = TextEditingController();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Adicionar funcionario'),
      ),
      body: Form(
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
}
