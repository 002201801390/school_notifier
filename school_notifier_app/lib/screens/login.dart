import 'package:flutter/material.dart';
import 'package:school_notifier_app/utils/login/login_utils.dart';

class Login extends StatelessWidget {
  final _controllerUsername = TextEditingController();
  final _controllerPassword = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Login"),
      ),
      body: _body(context),
    );
  }

  _body(BuildContext context) {
    return Form(
      key: _formKey,
      child: ListView(
        children: <Widget>[
          txtUsername(),
          txtPassword(),
          btnLogin(context),
        ],
      ),
    );
  }

  txtUsername() {
    return TextFormField(
      controller: _controllerUsername,
      validator: _validateUsername,
      keyboardType: TextInputType.text,
      decoration: InputDecoration(
        labelText: "Username",
        labelStyle: TextStyle(fontSize: 20.0),
      ),
    );
  }

  txtPassword() {
    return TextFormField(
      controller: _controllerPassword,
      validator: _validatePassword,
      obscureText: true,
      keyboardType: TextInputType.text,
      decoration: InputDecoration(
        labelText: "Password",
        labelStyle: TextStyle(fontSize: 20.0),
      ),
    );
  }

  btnLogin(BuildContext context) {
    return Container(
      height: 40.0,
      margin: EdgeInsets.only(top: 10),
      child: RaisedButton(
        child: Text("Login"),
        onPressed: () => _btnLoginPressed(context),
      ),
    );
  }

  void _btnLoginPressed(BuildContext context) {
    final username = _controllerUsername.text;
    final password = _controllerPassword.text;

    if (!_formKey.currentState.validate()) {
      return;
    }

    Future<int> response = LoginUtils.login(username, password);
    response.then((statusCode) async {
      if (statusCode == 200) {
        Navigator.pushNamed(context, '/dashboard');

        debugPrint('Login done successfully');
      } else if (statusCode == 401 || statusCode == 403) {
        debugPrint('Invalid credentials to login');
        showDialog(
          context: context,
          builder: (_) => _alertDialog(context, 'Credênciais inválidas'),
        );
      } else {
        debugPrint('Erro ao realizar login. Status Code: $statusCode');
        showDialog(
          context: context,
          builder: (_) => _alertDialog(
              context, 'Erro ao realizar login. Código: $statusCode'),
        );
      }
    });
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

  String _validateUsername(String username) {
    if (username.isEmpty) {
      return "Username is empty";
    }
    return null;
  }

  String _validatePassword(String password) {
    if (password.isEmpty) {
      return "Password is empty";
    }
    return null;
  }
}
