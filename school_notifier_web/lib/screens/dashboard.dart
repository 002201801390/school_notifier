import 'package:flutter/material.dart';

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
        body: _body(context),
      ),
    );
  }

  _body(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            _cardEmployee(context),
            _cardResponsible(context),
            _cardStudent(context),
          ],
        ),
      ],
    );
  }

  _cardEmployee(BuildContext context) {
    return Expanded(
      child: Card(
        child: ListTile(
          title: Text('Funcionários'),
          onTap: () => Navigator.pushNamed(context, '/dashboard/employee_list'),
        ),
      ),
    );
  }

  _cardResponsible(BuildContext context) {
    return Expanded(
      child: Card(
        child: ListTile(
          title: Text('Pais/Responsáveis'),
          onTap: () => Navigator.pushNamed(context, '/dashboard/responsible_list'),
        ),
      ),
    );
  }

  _cardStudent(BuildContext context) {
    return Expanded(
      child: Card(
        child: ListTile(
          title: Text('Alunos'),
          onTap: () => Navigator.pushNamed(context, '/dashboard/student_list'),
        ),
      ),
    );
  }
}
