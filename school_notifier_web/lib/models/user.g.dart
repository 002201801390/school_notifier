// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) {
  return User(
    id: json['id'] as String,
    name: json['name'] as String,
    cpf: json['cpf'] as String,
    dtBirth: json['dtBirth'] as String,
    email: json['email'] as String,
    phone: json['phone'] as String,
    username: json['username'] as String,
    password: json['password'] as String,
    role: json['role'] as String,
  );
}

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'cpf': instance.cpf,
      'dtBirth': instance.dtBirth,
      'email': instance.email,
      'phone': instance.phone,
      'username': instance.username,
      'password': instance.password,
      'role': instance.role,
    };
