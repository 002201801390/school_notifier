// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'schedule.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Schedule _$ScheduleFromJson(Map<String, dynamic> json) {
  return Schedule(
    daysOfWeek: (json['daysOfWeek'] as List)
        ?.map((e) => _$enumDecodeNullable(_$DAY_OF_WEEKEnumMap, e))
        ?.toList(),
    timeIni: json['timeIni'] as String,
    timeEnd: json['timeEnd'] as String,
  );
}

Map<String, dynamic> _$ScheduleToJson(Schedule instance) => <String, dynamic>{
      'daysOfWeek':
          instance.daysOfWeek?.map((e) => _$DAY_OF_WEEKEnumMap[e])?.toList(),
      'timeIni': instance.timeIni,
      'timeEnd': instance.timeEnd,
    };

T _$enumDecode<T>(
  Map<T, dynamic> enumValues,
  dynamic source, {
  T unknownValue,
}) {
  if (source == null) {
    throw ArgumentError('A value must be provided. Supported values: '
        '${enumValues.values.join(', ')}');
  }

  final value = enumValues.entries
      .singleWhere((e) => e.value == source, orElse: () => null)
      ?.key;

  if (value == null && unknownValue == null) {
    throw ArgumentError('`$source` is not one of the supported values: '
        '${enumValues.values.join(', ')}');
  }
  return value ?? unknownValue;
}

T _$enumDecodeNullable<T>(
  Map<T, dynamic> enumValues,
  dynamic source, {
  T unknownValue,
}) {
  if (source == null) {
    return null;
  }
  return _$enumDecode<T>(enumValues, source, unknownValue: unknownValue);
}

const _$DAY_OF_WEEKEnumMap = {
  DAY_OF_WEEK.MONDAY: 'MONDAY',
  DAY_OF_WEEK.TUESDAY: 'TUESDAY',
  DAY_OF_WEEK.WEDNESDAY: 'WEDNESDAY',
  DAY_OF_WEEK.THURSDAY: 'THURSDAY',
  DAY_OF_WEEK.FRIDAY: 'FRIDAY',
  DAY_OF_WEEK.SATURDAY: 'SATURDAY',
  DAY_OF_WEEK.SUNDAY: 'SUNDAY',
};
