import 'package:flutter/material.dart';
import 'package:school_notifier_web/components/text_field.dart';
import 'package:school_notifier_web/dao/class_dao.dart';
import 'package:school_notifier_web/dao/report_card_dao.dart';
import 'package:school_notifier_web/dao/student_dao.dart';
import 'package:school_notifier_web/models/class.dart';
import 'package:school_notifier_web/models/report_card.dart';
import 'package:school_notifier_web/models/user.dart';

class ReportCardView extends StatefulWidget {
  @override
  _ReportCardViewState createState() => _ReportCardViewState();
}

class _ReportCardViewState extends State<ReportCardView> {
  final _scoreController = TextEditingController();

  final GlobalKey<FormState> _formKey = GlobalKey();

  ReportCard _reportCard;

  List<DropdownMenuItem<User>> _studentItems = List();
  User _selectedStudent;
  List<DropdownMenuItem<Class>> _classItems = List();
  Class _selectedClass;

  @override
  Widget build(BuildContext context) {
    _reportCard = ModalRoute.of(context).settings.arguments;
    _selectedStudent = _reportCard.student;
    _selectedClass = _reportCard.clazz;
    _scoreController.text = _reportCard.score.toString();

    return Scaffold(
      appBar: AppBar(
        title: Text('Boletim'),
        centerTitle: true,
      ),
      body: _body(context, _reportCard),
    );
  }

  _body(BuildContext context, ReportCard reportCard) {
    return Column(
      children: [
        _reportCardPic(),
        Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
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
                  ],
                ),
                Container(padding: EdgeInsets.all(16)),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    _responsibleAckComp(),
                  ],
                )
              ],
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

  _responsibleAckComp() {
    return Row(
      children: [
        Text("Visto do responsável: "),
        Icon(_reportCard.responsibleAck ? Icons.check : Icons.clear),
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

                for (var item in snapshot.data) {
                  if (item.id == _selectedClass.id) {
                    _selectedClass = item;
                    break;
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

                  for (var item in snapshot.data) {
                    if (item.id == _selectedStudent.id) {
                      _selectedStudent = item;
                      break;
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

    bool success = await ReportCardDao.update(_getUpdatedReportCard());
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao atualizar boletim'),
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
            'Deseja realmente excluir este boletim? Está ação não poderá ser desfeita!'),
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

  ReportCard _getUpdatedReportCard() {
    _reportCard.student = _selectedStudent;
    _reportCard.clazz = _selectedClass;
    _reportCard.score = double.tryParse(_scoreController.text);
    return _reportCard;
  }

  _deleteUser(BuildContext context) async {
    Navigator.pop(context);

    final success = await ReportCardDao.delete(_reportCard);
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao excluir boletim'),
      );
      return;
    }
    Navigator.pop(context);
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
