import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';
import 'package:school_notifier_web/storage/storage.dart';

class Settings extends StatelessWidget {
  final _ipController = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Settings'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Form(
          key: _formKey,
          child: Center(
            child: Column(
              children: [
                TextComponents.textField(
                  label: 'IP',
                  controller: _ipController,
                  validator: _ipValidator,
                ),
                Spacer(flex: 1),
                RaisedButton(
                  child: Text('Save'),
                  onPressed: () => _btnSavePressed(context),
                ),
                Spacer(flex: 10),
              ],
            ),
          ),
        ),
      ),
    );
  }

  String _ipValidator(String ip) {
    if (ip == null || ip.isEmpty) {
      return "IP vazio";
    }
    return null;
  }

  _btnSavePressed(BuildContext context) async {
    if (!_formKey.currentState.validate()) {
      return;
    }

    await Storage.save('ip', _ipController.text);

    Navigator.pop(context);
  }
}
