import 'package:flutter/material.dart';

class TextComponents {
  static TextFormField textField(
      {String label,
      bool obscure,
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

  static Row prefixedTextField(String label, {double width}) {
    return Row(
      children: [
        Text(label),
        Container(
          child: TextComponents.textField(),
          width: width,
        )
      ],
    );
  }
}
