library structure;

import 'dart:html';
import 'dart:convert';
import 'dart:async';
import 'package:crypto/crypto.dart';
import 'package:logging/logging.dart';
import 'dart:mirrors';

part 'resource.dart';
part 'constants.dart';

class StructureReader {
  final Logger _log = new Logger("StructureReader");

  String baseUrl = const String.fromEnvironment("REST_URL", defaultValue: "http://laptop-dell-linux:8080/");
  Map<String, Map<String, RestResource>> groups = {};  // First key: group, second key: resource name

  Future queryStructure() {
    _log.finest("reading resources...");
    Future<HttpRequest> requestFuture = createJsonCall(JsonNames.STRUCTURE_URI);
    return requestFuture.then(handleStructureResponse);
  }

  Future handleStructureResponse(HttpRequest request) {
    Map decoded = JSON.decode(request.response);
    _log.finest("structure: $decoded");
    return Future.forEach(decoded.keys, (String group) => readGroup(group, decoded[group]));
  }

  Future readGroup(String group, Map<String, List<Map<String, String>>> groupContent) {
    _log.finest("group: $group, content: $groupContent");
    var list = groupContent[JsonNames.NON_HAL_LINKS];
    return Future.forEach(list, (Map<String, String> link) => readResourceLink(link));
  }

  Future readResourceLink(Map<String, String> link) {
    String rel = link[JsonNames.REL];
    String href = link[JsonNames.HREF];
    Future<HttpRequest> requestFuture = createJsonCall(href);
    return requestFuture.then((HttpRequest request) => readResource(JSON.decode(request.response)));
  }

  readResource(Map decoded) {
    _log.finest("resource: $decoded");
    RestResource resource = createResource(decoded);
    if(groups[resource.group] == null) {
      groups[resource.group] = {};
    }
    groups[resource.group][resource.name] = resource;
  }

  RestResource createResource(Map decoded) {
    if (decoded == null) {
      return null;
    }
    RestResource resource = new RestResource(decoded["name"], decoded["uri"], decoded["group"], decoded["type"]);
    _log.finest("resource: $resource");
    List<Map<String, String>> properties = decoded["properties"];
    properties.forEach((Map map) => resource.addProperty(readProperty(map)));
    return resource;
  }

  RestResourceProperty readProperty(Map map) {
    return new RestResourceProperty(map["name"], map["description"], enumFromString(map["type"], RestResourcePropertyType), map["format"], map["uri"], map["order"],
    createResource(map["dependentEntity"]), map["items"], map["required"], map["readOnly"]);
  }

  Future<HttpRequest> createJsonCall(String url, {String method : "GET", String data}) {
    var headers = {"Content-Type": "application/json"};
    String uri = url.startsWith("http") ? url : baseUrl + url;
    var future = HttpRequest.request(uri, method: method, sendData:data, withCredentials: true, mimeType: "application/json", requestHeaders: headers);
    return future;
  }


}
