import 'package:json_annotation/json_annotation.dart';

part 'user.g.dart';

@JsonSerializable()
class User {
  String id;
  String name;
  String cpf;
  String dtBirth;
  String email;
  String phone;
  String username;
  String password;
  String role;

  User(
      {this.id,
      this.name,
      this.cpf,
      this.dtBirth,
      this.email,
      this.phone,
      this.username,
      this.password,
      this.role});

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);

  Map<String, dynamic> toJson() => _$UserToJson(this);
}
