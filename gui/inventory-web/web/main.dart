import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';

void main() {
  Logger.root.level = Level.FINEST;
  Logger.root.onRecord.listen((LogRecord rec) {
    print('${rec.level.name}: ${rec.time}: ${rec.message}');
  });
  initPolymer();
}
