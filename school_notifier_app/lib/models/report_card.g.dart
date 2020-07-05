// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'report_card.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ReportCard _$ReportCardFromJson(Map<String, dynamic> json) {
  return ReportCard(
    id: json['id'] as String,
    student: json['student'] == null
        ? null
        : User.fromJson(json['student'] as Map<String, dynamic>),
    clazz: json['clazz'] == null
        ? null
        : Class.fromJson(json['clazz'] as Map<String, dynamic>),
    score: (json['score'] as num)?.toDouble(),
    responsibleAck: json['responsibleAck'] as bool,
  );
}

Map<String, dynamic> _$ReportCardToJson(ReportCard instance) =>
    <String, dynamic>{
      'id': instance.id,
      'student': instance.student,
      'clazz': instance.clazz,
      'score': instance.score,
      'responsibleAck': instance.responsibleAck,
    };
