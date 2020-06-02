import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';
import 'package:school_notifier_web/dao/student_dao.dart';
import 'package:school_notifier_web/models/user.dart';

class Student extends StatelessWidget {
  final _controllerName = TextEditingController();
  final _controllerUsername = TextEditingController();
  final _controllerPassword = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  User _student;

  @override
  Widget build(BuildContext context) {
    _student = ModalRoute.of(context).settings.arguments;

    return Scaffold(
      appBar: AppBar(
        title: Text('Aluno'),
        centerTitle: true,
      ),
      body: _body(context, _student),
    );
  }

  _body(BuildContext context, User student) {
    _controllerName.text = student.name;
    _controllerUsername.text = student.username;
    _controllerPassword.text = student.password;

    return Column(
      children: [
        _studentProfilePic(),
        Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  TextComponents.prefixedTextField(
                    'Nome:',
                    validator: _validatorName,
                    controller: _controllerName,
                    width: 300,
                  ),
                  TextComponents.prefixedTextField(
                    'Usuário:',
                    validator: _validatorUsername,
                    controller: _controllerUsername,
                    width: 300,
                  ),
                  TextComponents.prefixedTextField(
                    'Senha:',
                    validator: _validatorPassword,
                    controller: _controllerPassword,
                    width: 300,
                    obscure: true,
                  ),
                ],
              ),
            ),
          ),
        ),
        Expanded(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Spacer(flex: 10),
              RaisedButton(
                child: Text('Salvar'),
                onPressed: () => _btnSavePressed(context),
              ),
              Spacer(flex: 1),
              RaisedButton(
                color: Colors.redAccent,
                child: Text('Excluir'),
                onPressed: () => _btnDeletePressed(context),
              ),
              Spacer(flex: 10),
            ],
          ),
        ),
      ],
    );
  }

  Row _studentProfilePic() {
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

  String _validatorName(String name) {
    if (name == null || name.isEmpty) {
      return "Nome vazio";
    }
    return null;
  }

  String _validatorUsername(String username) {
    if (username == null || username.isEmpty) {
      return "Usuário vazio";
    }
    return null;
  }

  String _validatorPassword(String password) {
    if (password == null || password.isEmpty) {
      return "Senha vazia";
    }
    return null;
  }

  _btnSavePressed(BuildContext context) async {
    if (!_formKey.currentState.validate()) {
      return;
    }

    bool success = await StudentDao.update(_getUpdatedStudent());
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao atualizar aluno'),
      );
      return;
    }

    Navigator.pop(context);
  }

  _btnDeletePressed(BuildContext context) async {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text(
            'Deseja realmente excluir este aluno? Está ação não poderá ser desfeita!'),
        actions: [
          FlatButton(
            child: Text('Não'),
            onPressed: () => Navigator.pop(context),
          ),
          FlatButton(
            color: Colors.redAccent,
            child: Text('Sim'),
            onPressed: () => _deleteUser(context),
          ),
        ],
      ),
    );
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

  User _getUpdatedStudent() {
    _student.name = _controllerName.text;
    _student.username = _controllerUsername.text;
    _student.password = _controllerPassword.text;

    return _student;
  }

  _deleteUser(BuildContext context) async {
    Navigator.pop(context);

    final success = await StudentDao.delete(_student);
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao excluir aluno'),
      );
      return;
    }
    Navigator.pop(context);
  }
}
