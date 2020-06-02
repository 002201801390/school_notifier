import 'package:flutter/material.dart';

class TextComponents {
  static TextFormField textField({
    String label,
    bool obscure,
    TextEditingController controller,
    FormFieldValidator<String> validator,
  }) {
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

  static Row prefixedTextField(
    String label, {
    TextEditingController controller,
    FormFieldValidator<String> validator,
    String initialValue,
    double width,
    bool obscure,
  }) {
    return Row(
      children: [
        Text(label),
        Padding(
          padding: const EdgeInsets.only(left: 10),
          child: Container(
            child: TextComponents.textField(
              controller: controller,
              validator: validator,
              obscure: obscure,
            ),
            width: width,
          ),
        )
      ],
    );
  }
}
