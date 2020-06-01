import 'package:flutter/material.dart';
import 'package:school_notifier_web/dao/employee_dao.dart';
import 'package:school_notifier_web/models/user.dart';

class EmployeeList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Lista de Responsáveis'),
      ),
      body: _employeeListBody(),
    );
  }

  _employeeListBody() {
    return SafeArea(
      child: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            _buildEmployeeList(),
          ],
        ),
      ),
    );
  }

  _buildEmployeeList() {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(8, 8, 8, 0),
        child: FutureBuilder<List<User>>(
          future: EmployeeDao.find(),
          builder: (context, snapshot) {
            switch (snapshot.connectionState) {
              case ConnectionState.none:
              case ConnectionState.active:
                break;

              case ConnectionState.waiting:
                return _loadingData();

              case ConnectionState.done:
                if (snapshot.data != null) {
                  return _employeesListBuilder(snapshot);
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

  _employeesListBuilder(AsyncSnapshot<List<User>> snapshot) {
    final List<User> employees = snapshot.data;
    if (employees.isNotEmpty) {
      return ListView.builder(
        itemBuilder: (context, index) =>
            _buildEmployeeCard(context, employees[index]),
        itemCount: employees.length,
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

  _buildEmployeeCard(BuildContext context, User employee) {
    return Card(
      child: ListTile(
        title: Text(employee.name),
        leading: Icon(Icons.person),
        onTap: () => Navigator.pushNamed(context, '/dashboard/employee',
            arguments: employee),
      ),
    );
  }
}
