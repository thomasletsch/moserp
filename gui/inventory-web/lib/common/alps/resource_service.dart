library baseservice;

import 'dart:html';
import 'dart:convert';
import 'dart:async';
import 'package:logging/logging.dart';
import 'package:inventory/common/alps/structure.dart';

class ResourceService {
  final Logger _log = new Logger("ResourceService");
  static const String BASE_URL = 'http://localhost:8080/';

  ResourceService() {

  }

  String extractSelfUri(Map entity) {
    _log.finest("Extract self from $entity");
    if (!entity.containsKey(JsonNames.LINKS)) {
      return null;
    }
    Map links = entity[JsonNames.LINKS];
    if (!links.containsKey(JsonNames.SELF)) {
      return null;
    }
    Map self = links[JsonNames.SELF];
    if (!self.containsKey(JsonNames.HREF)) {
      return null;
    }
    return self[JsonNames.HREF];
  }

  String extractLinkUri(String name, Map entity) {
    _log.finest("Extract $name link from $entity");
    if (!entity.containsKey(JsonNames.LINKS)) {
      return null;
    }
    Map links = entity[JsonNames.LINKS];
    if (!links.containsKey(name)) {
      return null;
    }
    Map link = links[name];
    if (!link.containsKey(JsonNames.HREF)) {
      return null;
    }
    return link[JsonNames.HREF];
  }


  Future<Map> get(String uri) {
    var resourceName = calculateResourceName(uri);
    var fullUri = uri;
    if (!fullUri.startsWith(JsonNames.HTTP_PREFIX)) {
      fullUri = BASE_URL + fullUri;
    }
    _log.fine("Rest call for resource $resourceName with url $fullUri ");
    var future = createJsonCall(fullUri);
    return future.then((httpRequest) => retrieveResourceFromJSON(httpRequest, resourceName),
    onError: (httpRequest) => handleRelationNotFound(httpRequest, resourceName))
    .then((map) => new Future<Map>.value(map));
  }

  handleRelationNotFound(ProgressEvent event, String resourceName) {
    HttpRequest request = event.target;
    _log.finer("Got status ${request.status}. 404 means not found, which is ok and ignored.");
  }

  Future<List> getList(String uri) {
    var resourceName = calculateResourceName(uri);
    var fullUri = uri;
    if (!fullUri.startsWith(JsonNames.HTTP_PREFIX)) {
      fullUri = BASE_URL + fullUri;
    }
    _log.fine("Rest call for resource $resourceName with url $fullUri ");
    var future = createJsonCall(fullUri);
    return future.then((httpRequest) => retrieveListFromJSON(httpRequest, resourceName)).then((list) => new Future<List>.value(list));
  }

  Future post(Map resource, String uri) {
    String resourceName = calculateResourceName(uri);
    var fullUri = uri;
    if (!fullUri.startsWith(JsonNames.HTTP_PREFIX)) {
      fullUri = BASE_URL + fullUri;
    }
    _log.info("POST Rest call for resource $resourceName with url $fullUri ");
    return createJsonCall(fullUri, method: "POST", data: JSON.encode(resource));
  }

  Future put(Map resource, String uri) {
    var fullUri = uri;
    if (!fullUri.startsWith(JsonNames.HTTP_PREFIX)) {
      fullUri = BASE_URL + fullUri;
    }
    _log.info("PUT Rest call for resource with uri $fullUri ");
    return createJsonCall(fullUri, method: "PUT", data: JSON.encode(resource));
  }

  Future delete(String uri) {
    var resourceName = calculateResourceName(uri);
    var fullUri = uri;
    if (!fullUri.startsWith(JsonNames.HTTP_PREFIX)) {
      fullUri = BASE_URL + fullUri;
    }
    _log.info("DELETE Rest call for resource $resourceName with url $fullUri ");
    return createJsonCall(fullUri, method: "DELETE");
  }

  String calculateResourceName(String uri) {
    _log.finer("Calculate Resource Name from $uri");
    RegExp searchUri = new RegExp(r"^(http.*\/)(\w+)(\/search\/.*)");
    RegExp resourceUri = new RegExp(r"^(http.*\/)(\w+)");
    _log.finest("searchUri $searchUri");
    _log.finest("resourceUri $resourceUri");
    String resourceName;
    if (searchUri.hasMatch(uri)) {
      _log.finest("matches $searchUri");
      resourceName = searchUri.firstMatch(uri).group(2);
    } else if (resourceUri.hasMatch(uri)) {
      _log.finest("matches $resourceUri");
      resourceName = resourceUri.firstMatch(uri).group(2);
    } else {
      resourceName = uri;
    }
    return resourceName;
  }


  Map retrieveResourceFromJSON(HttpRequest req, String element) {
    _log.fine("REST Response: ${req.response}");
    Map data = JSON.decode(req.response);
    _log.finest("Decoded: $data");
    return data;
  }

  List retrieveListFromJSON(HttpRequest req, String element) {
    _log.finer("REST Response: ${req.response}");
    Map data = JSON.decode(req.response);
    _log.finest("Decoded: $data");
    if (!data.containsKey(JsonNames.EMBEDDED)) {
      return [];
    }
    Map embedded = data[JsonNames.EMBEDDED];
    _log.finest("embedded: $embedded with element: $element");
    if (!embedded.containsKey(element)) {
      element = embedded.keys.first;
      if (element == null) {
        return [];
      }
    }
    List list = embedded[element];
    _log.finest("list: $list");
    return list;
  }

  Future createJsonCall(String url, {String method : "GET", String data}) {
    var headers = {"Content-Type": "application/hal+json"};
    var future = HttpRequest.request(url, method: method, sendData:data, withCredentials: true, mimeType: "application/json", requestHeaders: headers);
    return future;
  }

}