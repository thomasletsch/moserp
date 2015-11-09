library convertion;
import 'package:inventory/common/alps/resource_service.dart';
import 'package:inventory/common/alps/structure.dart';

class ConversionService {

  ResourceService service = new ResourceService();

  String convert(var source) {
    if(source == null) {
      return "";
    }
    return "$source";
  }

  String convertToValue(RestResourceProperty property, Map resource) {
    String result;
    if (property.type == RestResourcePropertyType.VALUE) {
      result =  resource["value"];
    } else {
      result =  service.extractSelfUri(resource);
    }
    if(result == null) {
      result = "";
    }
    return result;
  }

  String convertToLabel(RestResourceProperty property, Map resource) {
    if(resource == null) {
      return "";
    }
    if(resource["displayName"] is String) {
      return resource["displayName"];
    }
    else {
      return "${resource["displayName"]}";
    }
  }
}