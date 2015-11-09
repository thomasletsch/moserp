library i18n;

import 'package:logging/logging.dart';
import 'dart:html';
import 'dart:convert';
import 'dart:async';

class TranslationService {
  static final TranslationService _singleton = new TranslationService._internal();

  final Logger _log = new Logger("Translation");

  Map _TRANSLATIONS;
  Future _loader;
  Completer _finisher = new Completer<VoidCallback>();

  factory TranslationService() {
    return _singleton;
  }

  TranslationService._internal() {
    _loadTranslations();
  }

  void _loadTranslations() {
    if (_TRANSLATIONS != null) {
      return;
    }
    String basePath = "";
    if(Uri.base.pathSegments.length > 1) {
      basePath = "/${Uri.base.pathSegments[0]}";
    }
    _log.finer("Load Translations from '$basePath/packages/inventory/translations/messages.json'");
    _loader = HttpRequest.getString('$basePath/packages/inventory/translations/messages.json');
    _loader.then((res) {
      try {
        _TRANSLATIONS = JSON.decode(res);
      } catch (e, stacktrace) {
        _log.severe("Could not parse translation messages.json: $e - $stacktrace");
      }
      _log.finer("Loading complete. Completing finisher...");
      _finisher.complete(null);
    });
    _loader.catchError((e) => _finisher.complete(null));
  }

  Future<String> translate(String key) {
    Completer translator = new Completer<String>();
    if (_finisher.isCompleted) {
      translator.complete(_translateInternally(key));
    } else {
      _finisher.future.then((value) => translator.complete(_translateInternally(key)));
    }
    return translator.future;
  }

  String translateDirect(String key) {
    return _translateInternally(key);
  }

  String _translateInternally(String key) {
    _log.finest("Translate key $key");
    if (key == null) {
      throw new ArgumentError("key is NULL");
    }
    String translated = key;
    if (_TRANSLATIONS != null && _TRANSLATIONS.containsKey(key)) {
      translated = _TRANSLATIONS[key];
      _log.finest("Translation: $translated");
    } else {
      _log.fine("No Translation for key $key found!");
    }
    return translated;
  }

}