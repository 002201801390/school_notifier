import 'package:flutter/material.dart';
import 'package:school_notifier_web/dao/report_card_dao.dart';
import 'package:school_notifier_web/models/report_card.dart';

class ReportCardList extends StatefulWidget {
  @override
  _ReportCardListState createState() => _ReportCardListState();
}

class _ReportCardListState extends State<ReportCardList> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Boletins'),
      ),
      body: _reportCardListBody(),
      floatingActionButton: _buildAddReportCard(context),
    );
  }

  _reportCardListBody() {
    return SafeArea(
      child: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            _buildReportCardList(),
          ],
        ),
      ),
    );
  }

  _buildReportCardList() {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(8, 8, 8, 0),
        child: FutureBuilder<List<ReportCard>>(
          future: ReportCardDao.find(),
          builder: (context, snapshot) {
            switch (snapshot.connectionState) {
              case ConnectionState.none:
              case ConnectionState.active:
                break;

              case ConnectionState.waiting:
                return _loadingData();

              case ConnectionState.done:
                if (snapshot.data != null) {
                  return _reportCardsListBuilder(snapshot);
                }
                break;
            }
            debugPrint("This should not happen...");
            return Text('Something went wrong while loading list...');
          },
        ),
      ),
    );
  }

  _loadingData() {
    return Center(
      child: CircularProgressIndicator(),
    );
  }

  _reportCardsListBuilder(AsyncSnapshot<List<ReportCard>> snapshot) {
    final List<ReportCard> reportCards = snapshot.data;
    if (reportCards.isNotEmpty) {
      return ListView.builder(
        itemBuilder: (context, index) =>
            _buildReportCard(context, reportCards[index]),
        itemCount: reportCards.length,
      );
    }
    return Center(
      child: Text(
        'Nenhum item encontrado',
        style: TextStyle(fontSize: 24),
      ),
    );
  }

  _buildReportCard(BuildContext context, ReportCard reportCard) {
    var title = 'Aluno: ${reportCard.student.name}';
    return Card(
      child: ListTile(
        title: Text(title),
        leading: Icon(Icons.assignment),
        subtitle: Text(
            'Matéria: ${reportCard.clazz.discipline.name}, Professor: ${reportCard.clazz.teacher.name}'),
        trailing: Text('Nota: ${reportCard.score}'),
        onTap: () => Navigator.pushNamed(context, '/dashboard/report_card',
                arguments: reportCard)
            .then((value) => setState(() => _buildReportCardList())),
      ),
    );
  }

  _buildAddReportCard(BuildContext context) {
    return FloatingActionButton.extended(
      label: Text(
        'Adicionar Boletim',
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
      icon: Icon(Icons.add),
      onPressed: () =>
          Navigator.pushNamed(context, '/dashboard/report_card_list/report_card_add')
              .then((value) => setState(() => _buildReportCardList())),
    );
  }
}
