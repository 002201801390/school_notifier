import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';
import 'package:school_notifier_web/dao/class_dao.dart';
import 'package:school_notifier_web/dao/report_card_dao.dart';
import 'package:school_notifier_web/dao/student_dao.dart';
import 'package:school_notifier_web/models/class.dart';
import 'package:school_notifier_web/models/report_card.dart';
import 'package:school_notifier_web/models/user.dart';

class ReportCardAdd extends StatefulWidget {
  @override
  _ReportCardAddState createState() => _ReportCardAddState();
}

class _ReportCardAddState extends State<ReportCardAdd> {
  final _scoreController = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  List<DropdownMenuItem<User>> _studentItems = List();

  User _selectedStudent;

  List<DropdownMenuItem<Class>> _classItems = List();

  Class _selectedClass;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Adicionar boletim'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: _body(context),
      ),
    );
  }

  _body(BuildContext context) {
    return Column(
      children: [
        _reportCardPic(),
        Form(
          key: _formKey,
          child: Column(
            children: [
              Container(padding: EdgeInsets.all(16)),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  _studentComp(),
                  Container(padding: EdgeInsets.all(8)),
                  _classComp(),
                  Container(padding: EdgeInsets.all(8)),
                  _scoreComp(),
                  RaisedButton(
                    child: Text('Cadastrar'),
                    onPressed: () => _btnSavePressed(context),
                  ),
                ],
              ),
              Container(padding: EdgeInsets.all(16)),
            ],
          ),
        ),
      ],
    );
  }

  _reportCardPic() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Center(
          child: Icon(
            Icons.assignment,
            size: 256,
          ),
        ),
      ],
    );
  }

  _scoreComp() {
    return Row(
      children: [
        Text("Nota: "),
        Container(
          width: 110,
          height: 50,
          child: TextComponents.textField(
            validator: _scoreValidator,
            controller: _scoreController,
            keyboardType: TextInputType.numberWithOptions(decimal: true),
            textAlign: TextAlign.center,
          ),
        ),
      ],
    );
  }

  _classComp() {
    return Row(children: [
      Text("Classe: "),
      FutureBuilder<List<Class>>(
        future: ClassDao.find(),
        builder: (context, snapshot) {
          switch (snapshot.connectionState) {
            case ConnectionState.none:
            case ConnectionState.active:
            case ConnectionState.waiting:
              break;

            case ConnectionState.done:
              if (snapshot.data != null) {
                _classItems = snapshot.data
                    .map((e) => DropdownMenuItem(
                          value: e,
                          child: Text(e.discipline.name),
                        ))
                    .toList();

                if (_selectedClass != null) {
                  for (var item in snapshot.data) {
                    if (item.id == _selectedClass.id) {
                      _selectedClass = item;
                      break;
                    }
                  }
                }

                return DropdownButton(
                  value: _selectedClass,
                  items: _classItems,
                  onChanged: changedDropdownClass,
                );
              }
              break;
          }
          return Text('Something went wrong while loading list...');
        },
      )
    ]);
  }

  _studentComp() {
    return Row(
      children: [
        Text("Aluno: "),
        FutureBuilder<List<User>>(
          future: StudentDao.find(),
          builder: (context, snapshot) {
            switch (snapshot.connectionState) {
              case ConnectionState.none:
              case ConnectionState.active:
              case ConnectionState.waiting:
                break;

              case ConnectionState.done:
                if (snapshot.data != null) {
                  _studentItems = snapshot.data
                      .map((e) => DropdownMenuItem(
                            value: e,
                            child: Text(e.name),
                          ))
                      .toList();

                  if (_selectedStudent != null) {
                    for (var item in snapshot.data) {
                      if (item.id == _selectedStudent.id) {
                        _selectedStudent = item;
                        break;
                      }
                    }
                  }
                  return DropdownButton(
                    value: _selectedStudent,
                    items: _studentItems,
                    onChanged: changedDropdownStudent,
                  );
                }
                break;
            }
            return Text('Something went wrong while loading list...');
          },
        ),
      ],
    );
  }

  _btnSavePressed(BuildContext context) async {
    if (!_formKey.currentState.validate()) {
      return;
    }

    final success = await ReportCardDao.save(_reportCardData());
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao salvar boletim'),
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

  _reportCardData() {
    return ReportCard(
      clazz: _selectedClass,
      student: _selectedStudent,
      score: double.tryParse(_scoreController.text),
      responsibleAck: false,
    );
  }

  void changedDropdownStudent(User student) {
    setState(() {
      _selectedStudent = student;
    });
  }

  void changedDropdownClass(Class clazz) {
    setState(() {
      _selectedClass = clazz;
    });
  }

  String _scoreValidator(String scoreText) {
    if (scoreText == null || scoreText.isEmpty) {
      return "Nota vazia";
    }
    double score = double.tryParse(scoreText);
    if (score == null) {
      return "Nota vazia";
    }

    if (score > 10) {
      return "Nota maior que 10";
    }

    if (score < 0) {
      return "Nota menor que 0";
    }
    return null;
  }
}
