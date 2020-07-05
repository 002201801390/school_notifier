import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:school_notifier_app/models/report_card.dart';
import 'package:school_notifier_app/utils/http/http_utils.dart';

class ReportCardDao {
  static Future<bool> save(ReportCard reportCard) async {
    final reportCardJson = jsonEncode(reportCard.toJson());

    var response =
        await HttpUtils.doPost('/report_card/insert', reportCardJson, true);

    return response.statusCode == 200;
  }

  static Future<List<ReportCard>> find() async {
    final http.Response response =
        await HttpUtils.doGet('/report_card/my', true);

    if (response.statusCode == 200) {
      final List<ReportCard> reportCards = List();
      final List<dynamic> items = jsonDecode(response.body);

      for (var item in items) {
        ReportCard responsible = ReportCard.fromJson(item);
        reportCards.add(responsible);
      }

      return reportCards;
    }
    return null;
  }

  static Future<bool> update(ReportCard reportCard) async {
    final reportCardJson = jsonEncode(reportCard.toJson());

    var response =
        await HttpUtils.doPost('/report_card/update', reportCardJson, true);

    return response.statusCode == 200;
  }

  static Future<bool> delete(ReportCard reportCard) async {
    final reportCardJson = jsonEncode(reportCard.toJson());

    var response =
        await HttpUtils.doPost('/report_card/delete', reportCardJson, true);

    return response.statusCode == 200;
  }

  static Future<bool> ack(ReportCard reportCard) async {
    reportCard.responsibleAck = true;
    return update(reportCard);
  }
}
