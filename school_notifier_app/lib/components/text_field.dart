import 'package:flutter/material.dart';

class TextComponents {
  static TextFormField textField({
    String label,
    bool obscure,
    TextEditingController controller,
    FormFieldValidator<String> validator,
    TextInputType keyboardType,
    InputDecoration decoration,
    TextAlign textAlign,
  }) {
    return TextFormField(
      obscureText: true == obscure,
      controller: controller,
      validator: validator,
      keyboardType: keyboardType != null ? keyboardType : TextInputType.text,
      decoration: decoration != null
          ? decoration
          : InputDecoration(
              labelText: label,
              labelStyle: TextStyle(fontSize: 20.0),
            ),
      textAlign: textAlign != null ? textAlign : TextAlign.start,
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
