import 'package:flutter/material.dart';
import 'package:school_notifier_web/dao/responsible_dao.dart';
import 'package:school_notifier_web/models/user.dart';

class ResponsibleList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Lista de Responsáveis'),
      ),
      body: _responsibleListBody(),
    );
  }

  _responsibleListBody() {
    return SafeArea(
      child: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            _buildResponsibleList(),
          ],
        ),
      ),
    );
  }

  _buildResponsibleList() {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(8, 8, 8, 0),
        child: FutureBuilder<List<User>>(
          future: ResponsibleDao.find(),
          builder: (context, snapshot) {
            switch (snapshot.connectionState) {
              case ConnectionState.none:
              case ConnectionState.active:
                break;

              case ConnectionState.waiting:
                return _loadingData();

              case ConnectionState.done:
                if (snapshot.data != null) {
                  return _responsiblesListBuilder(snapshot);
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

  _responsiblesListBuilder(AsyncSnapshot<List<User>> snapshot) {
    final List<User> responsibles = snapshot.data;

    if (responsibles.isNotEmpty) {
      return ListView.builder(
        itemBuilder: (context, index) =>
            _buildResponsibleCard(context, responsibles[index]),
        itemCount: responsibles.length,
      );
    }

    return Center(
      child: Text(
        'Nenhum item encontrado',
        style: TextStyle(fontSize: 24),
      ),
    );
  }

  _loadingData() {
    return Center(
      child: CircularProgressIndicator(),
    );
  }

  _buildResponsibleCard(BuildContext context, User responsible) {
    return Card(
      child: ListTile(
        title: Text(responsible.name),
        leading: Icon(Icons.person),
        onTap: () => Navigator.pushNamed(context, '/dashboard/responsible',
            arguments: responsible),
      ),
    );
  }
}
