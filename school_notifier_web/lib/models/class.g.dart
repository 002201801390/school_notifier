// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'class.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Class _$ClassFromJson(Map<String, dynamic> json) {
  return Class(
    id: json['id'] as String,
    discipline: json['discipline'] == null
        ? null
        : Discipline.fromJson(json['discipline'] as Map<String, dynamic>),
    teacher: json['teacher'] == null
        ? null
        : User.fromJson(json['teacher'] as Map<String, dynamic>),
    schedule: json['schedule'] == null
        ? null
        : Schedule.fromJson(json['schedule'] as Map<String, dynamic>),
    students: (json['students'] as List)
        ?.map(
            (e) => e == null ? null : User.fromJson(e as Map<String, dynamic>))
        ?.toList(),
  );
}

Map<String, dynamic> _$ClassToJson(Class instance) => <String, dynamic>{
      'id': instance.id,
      'discipline': instance.discipline,
      'teacher': instance.teacher,
      'schedule': instance.schedule,
      'students': instance.students,
    };
