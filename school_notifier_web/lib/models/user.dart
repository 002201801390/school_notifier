class User {
  String id;
  String name;
  String cpf;
  DateTime dtBirth;
  String email;
  String phone;
  String username;
  String password;
  String role;


  User({this.id, this.name, this.cpf, this.dtBirth, this.email, this.phone,
      this.username, this.password, this.role});

  Map<String, dynamic> toJson() => {
        'id': id,
        'name': name,
        'cpf': cpf,
        'dtBirth': dtBirth,
        'email': email,
        'phone': phone,
        'username': username,
        'password': password,
        'role': role,
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
        password = json['password'],
        role = json['role'];
}
