import 'package:flutter/material.dart';
import 'package:school_notifier_app/dao/report_card_dao.dart';
import 'package:school_notifier_app/models/report_card.dart';
import 'package:school_notifier_app/screens/dashboard/dashboard_menu.dart';

class Dashboard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Scaffold(
        appBar: AppBar(
          title: Text('Dashboard'),
          automaticallyImplyLeading: false,
          centerTitle: true,
        ),
        body: _reportCardListBody(),
        drawer: DashboardMenu(),
      ),
    );
  }

  _reportCardListBody() {
    return SafeArea(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          _buildReportCardList(),
        ],
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
                return _reportCardListBuilder(snapshot);
            }
            debugPrint("This should not happen...");
            return Text('Something went wrong while loading list...');
          },
        ),
      ),
    );
  }

  ListView _reportCardListBuilder(AsyncSnapshot<List<ReportCard>> snapshot) {
    final List<ReportCard> students = snapshot.data;

    return ListView.builder(
      itemBuilder: (context, index) =>
          _buildReportCardItem(context, students[index]),
      itemCount: students.length,
    );
  }

  _loadingData() {
    return Center(
      child: CircularProgressIndicator(),
    );
  }

  _buildReportCardItem(BuildContext context, ReportCard reportCard) {
    return Card(
      child: ListTile(
        title: Text(reportCard.student.name),
        subtitle: Text(
          'Matéria: ${reportCard.clazz.discipline.name}, Professor: ${reportCard.clazz.teacher.name}',
        ),
        leading: Icon(
          Icons.assignment,
          color: reportCard.responsibleAck ? null : Colors.redAccent,
        ),
        trailing: Text(
          'Nota: ${reportCard.score}',
          style:
              TextStyle(color: reportCard.score >= 5 ? null : Colors.redAccent),
        ),
        onTap: () => Navigator.pushNamed(context, '/dashboard/report_card',
            arguments: reportCard),
      ),
    );
  }
}
