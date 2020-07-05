import 'package:json_annotation/json_annotation.dart';

part 'discipline.g.dart';

@JsonSerializable()
class Discipline {
  String id;
  String name;

  Discipline({
    this.id,
    this.name,
  });


  factory Discipline.fromJson(Map<String, dynamic> json) => _$DisciplineFromJson(json);

  Map<String, dynamic> toJson() => _$DisciplineToJson(this);
}
