class User {
  String id;
  String name;
  String cpf;
  DateTime dtBirth;
  String email;
  String phone;
  String username;
  String password;

  Map<String, dynamic> toJson() => {
        'id': id,
        'name': name,
        'cpf': cpf,
        'dtBirth': dtBirth,
        'email': email,
        'phone': phone,
        'username': username,
        'password': password,
      };

  @override
  User.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        name = json['name'],
        cpf = json['cpf'],
        dtBirth = json['dtBirth'],
        email = json['email'],
        phone = json['phone'],
        username = json['username'],
        password = json['password'];
}
