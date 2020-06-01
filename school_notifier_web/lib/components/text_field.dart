import 'package:flutter/material.dart';

class TextComponents {
  static textField(String label,
      {bool obscure,
      TextEditingController controller,
      FormFieldValidator<String> validator}) {
    return TextFormField(
      obscureText: true == obscure,
      controller: controller,
      validator: validator,
      keyboardType: TextInputType.text,
      decoration: InputDecoration(
        labelText: label,
        labelStyle: TextStyle(fontSize: 20.0),
      ),
    );
  }
}
