import 'package:flutter/material.dart';
import 'package:school_notifier_app/dao/report_card_dao.dart';
import 'package:school_notifier_app/models/class.dart';
import 'package:school_notifier_app/models/report_card.dart';
import 'package:school_notifier_app/models/user.dart';

class ReportCardView extends StatefulWidget {
  @override
  _ReportCardViewState createState() => _ReportCardViewState();
}

class _ReportCardViewState extends State<ReportCardView> {
  ReportCard _reportCard;
  User _selectedStudent;
  Class _selectedClass;

  @override
  Widget build(BuildContext context) {
    _reportCard = ModalRoute.of(context).settings.arguments;
    _selectedStudent = _reportCard.student;
    _selectedClass = _reportCard.clazz;

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
        Container(padding: EdgeInsets.all(16)),
        _reportCardPic(),
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(padding: EdgeInsets.all(16)),
                  _studentComp(),
                  Container(padding: EdgeInsets.all(8)),
                  _classComp(),
                  Container(padding: EdgeInsets.all(8)),
                  _scoreComp(),
                  Container(padding: EdgeInsets.all(16)),
                  _responsibleAckComp(),
                ],
              ),
            ],
          ),
        ),
        Expanded(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Spacer(flex: 10),
              RaisedButton(
                child: Text('Vistar'),
                onPressed: _reportCard.responsibleAck
                    ? null
                    : () => _btnAckPressed(context),
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
            size: 128,
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
        Text("Nota: ${_reportCard.score}"),
      ],
    );
  }

  _classComp() {
    return Row(children: [
      Text(
        "Classe: ${_selectedClass.discipline.name}",
        style: TextStyle(
          fontSize: 16,
        ),
      ),
    ]);
  }

  _studentComp() {
    return Row(
      children: [
        Text(
          "Aluno: ${_selectedStudent.name}",
          style: TextStyle(fontSize: 16),
        ),
      ],
    );
  }

  _btnAckPressed(BuildContext context) async {
    _reportCard.responsibleAck = true;
    bool success = await ReportCardDao.ack(_reportCard);
    if (!success) {
      showDialog(
        context: context,
        builder: (_) => _alertDialog(context, 'Erro ao assinar boletim'),
      ).then((value) => setState(() => _body(context, _reportCard)));
      return;
    } else {
      setState(() => _body(context, _reportCard));
    }
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
}
