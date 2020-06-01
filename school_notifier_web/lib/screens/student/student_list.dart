import 'package:flutter/material.dart';
import 'package:school_notifier_web/dao/student_dao.dart';
import 'package:school_notifier_web/models/user.dart';

class StudentList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Lista de Alunos'),
      ),
      body: _studentListBody(),
      floatingActionButton: _buildAddStudent(context),
    );
  }

  _studentListBody() {
    return SafeArea(
      child: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            _buildStudentList(),
          ],
        ),
      ),
    );
  }

  _buildStudentList() {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(8, 8, 8, 0),
        child: FutureBuilder<List<User>>(
          future: StudentDao.find(),
          builder: (context, snapshot) {
            switch (snapshot.connectionState) {
              case ConnectionState.none:
              case ConnectionState.active:
                break;

              case ConnectionState.waiting:
                return _loadingData();

              case ConnectionState.done:
                if (snapshot.data != null) {
                  return _studentsListBuilder(snapshot);
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

  _studentsListBuilder(AsyncSnapshot<List<User>> snapshot) {
    final List<User> students = snapshot.data;

    if (students.isNotEmpty) {
      return ListView.builder(
        itemBuilder: (context, index) =>
            _buildStudentCard(context, students[index]),
        itemCount: students.length,
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

  _buildStudentCard(BuildContext context, User student) {
    return Card(
      child: ListTile(
        title: Text(student.name),
        leading: Icon(Icons.person),
        onTap: () => Navigator.pushNamed(context, '/dashboard/student',
            arguments: student),
      ),
    );
  }

  _buildAddStudent(BuildContext context) {
    return FloatingActionButton.extended(
      label: Text(
        'Adicionar Aluno',
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
      icon: Icon(Icons.add),
      onPressed: () =>
          Navigator.pushNamed(context, '/dashboard/student_list/student_add'),
    );
  }
}
