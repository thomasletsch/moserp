part of structure;

class RestResourceGroup {

  List<RestResource> resources = [];

}

class RestResource {

  String name;
  String uri;
  String group;
  String type;
  List<RestResourceProperty> properties = [];

  RestResource(this.name, this.uri, this.group, this.type);

  addProperty(RestResourceProperty property) => properties.add(property);

  String toString() {
    return "RestResource: name: $name, uri: $uri";
  }


}

class RestResourceProperty {
  String name;
  String description;
  RestResourcePropertyType type;
  String format;
  String uri;
  int order;
  List<String> items;
  RestResource dependentResource;
  bool required;
  bool readOnly;

  RestResourceProperty(this.name, this.description, this.type, this.format, this.uri, this.order, this.dependentResource, this.items, this.required, this.readOnly);

}

dynamic enumFromString(String value, t) {
  if (value == null)
    return null;
  return (reflectType(t) as ClassMirror).getField(#values).reflectee.firstWhere((e) => e.toString().split('.')[1].toUpperCase() == value.toUpperCase());
}

enum RestResourcePropertyType {

  COLLECTION, BOOLEAN, STRING, INTEGER, NUMBER, ENUM, DATE, VALUE, DEPENDENT_ENTITY, ASSOCIATION

}