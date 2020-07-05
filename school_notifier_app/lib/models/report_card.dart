import 'package:json_annotation/json_annotation.dart';

import 'package:school_notifier_app/models/class.dart';
import 'package:school_notifier_app/models/user.dart';

part 'report_card.g.dart';

@JsonSerializable()
class ReportCard {
  String id;
  User student;
  Class clazz;
  double score;
  bool responsibleAck;

  ReportCard({
    this.id,
    this.student,
    this.clazz,
    this.score,
    this.responsibleAck,
  });

  factory ReportCard.fromJson(Map<String, dynamic> json) => _$ReportCardFromJson(json);

  Map<String, dynamic> toJson() => _$ReportCardToJson(this);
}
