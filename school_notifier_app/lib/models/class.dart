import 'package:json_annotation/json_annotation.dart';

import 'package:school_notifier_app/models/discipline.dart';
import 'package:school_notifier_app/models/schedule.dart';
import 'package:school_notifier_app/models/user.dart';

part 'class.g.dart';

@JsonSerializable()
class Class {
  String id;
  Discipline discipline;
  User teacher;
  Schedule schedule;
  List<User> students;

  Class({
    this.id,
    this.discipline,
    this.teacher,
    this.schedule,
    this.students,
  });

  factory Class.fromJson(Map<String, dynamic> json) => _$ClassFromJson(json);

  Map<String, dynamic> toJson() => _$ClassToJson(this);
}
