import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';
import 'package:inventory/common/translation.dart';

@CustomTag('polymer-translate')
class PolymerTranslate extends PolymerElement {
  final Logger _log = new Logger("PolymerTranslate");

  @published String locale = 'de';
  @published String key;
  @observable String translated;

  TranslationService _translationService = new TranslationService();

  PolymerTranslate.created(): super.created(){
  }

  keyChanged() {
    _translationService.translate(key).then((value) => translated = value);
  }

}