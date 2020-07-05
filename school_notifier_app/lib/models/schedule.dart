import 'package:json_annotation/json_annotation.dart';

import 'package:school_notifier_app/enums/DAY_OF_WEEK.dart';

part 'schedule.g.dart';

@JsonSerializable()
class Schedule {
  List<DAY_OF_WEEK> daysOfWeek;
  String timeIni;
  String timeEnd;

  Schedule({
    this.daysOfWeek,
    this.timeIni,
    this.timeEnd,
  });

  factory Schedule.fromJson(Map<String, dynamic> json) =>
      _$ScheduleFromJson(json);

  Map<String, dynamic> toJson() => _$ScheduleToJson(this);
}
