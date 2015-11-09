import 'dart:html';
import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';
import 'package:paper_elements/paper_action_dialog.dart';
import 'package:paper_elements/paper_button.dart';
import 'package:inventory/elements/flexible_table.dart';
import 'package:inventory/common/alps/resource_service.dart';
import 'package:inventory/common/alps/structure.dart';
import 'resource_edit_button.dart';
import 'resource_delete_button.dart';
import 'package:inventory/elements/resource_page.dart';
import 'package:inventory/elements/sub_resource.dart';
import 'package:inventory/common/conversion.dart';

@CustomTag('resource-list')
class ResourceList extends PolymerElement implements CellClickListener {
  final Logger log = new Logger("ResourceList");

  ResourceService service;

  @published RestResource resource;
  @published ResourcePage resourcePage;
  @observable List<Map> entities;
  @observable List<String> columns = [];
  @observable Map<String, CellFactory> cellFactories = {};
  @observable CellClickListener cellClickListener;

  ResourceList.created() : super.created(){
    service = new ResourceService();
    cellClickListener = this;
  }

  resourceChanged() {
    populateColumns();
    queryResourceList();
  }

  void queryResourceList() {
    service.getList(resource.uri).then((list) => populateResourceList(list));
  }

  populateColumns() {
    if (resource == null) {
      return;
    }
    log.fine("Resource: $resource");
    log.fine("Resource properties: ${resource.properties}");
    List properties = resource.properties.toList();
    properties.sort((RestResourceProperty a, RestResourceProperty b) => a.order.compareTo(b.order));
    columns = []
      ..addAll(properties.map((RestResourceProperty property) => property.name))
      ..add("RESOURCE_EDIT")
      ..add("RESOURCE_DELETE");
    cellFactories["RESOURCE_EDIT"] = new ResourceEditCellFactory(resourcePage);
    cellFactories["RESOURCE_DELETE"] = new ResourceDeleteCellFactory(this);
    List<RestResourceProperty> subResources = resource.properties.where((RestResourceProperty property) => property.type == "array").toList();
    subResources.forEach((RestResourceProperty property) => cellFactories[property.name] = new SubResourceCellFactory(this, property));
    List<RestResourceProperty> manyToOneRelations = resource.properties.where((RestResourceProperty property) => (property.format == "uri") && (property .type != "value")).toList();
    manyToOneRelations.forEach((RestResourceProperty property) => cellFactories[property.name] = new ManyToOneCellFactory(this, property));
  }

  populateResourceList(List<Map> list) {
    entities = toObservable(list);
  }

  @override
  void cellClicked(int row, int column, Element target) {
    log.fine("Cell was clicked: $row, $column");
  }

}

class ResourceEditCellFactory extends CellFactory {
  final Logger log = new Logger("ResourceList");

  ResourcePage resourcePage;
  ResourceService service = new ResourceService();

  ResourceEditCellFactory(this.resourcePage);

  @override
  Element createCell(CellContext context) {
    ResourceEditButton element = new Element.tag("resource-edit-button");
    var uri = service.extractSelfUri(context.row);
    element.uri = uri;
    element.onClick.listen((MouseEvent e) {
      log.fine("Mouse clicked on ${e.target} with uri ${uri}, resourcePage: $resourcePage");
      service.get(uri).then((Map data) => resourcePage.editResource(data));
    });
    return element;
  }
}

class ResourceDeleteCellFactory extends CellFactory {
  final Logger log = new Logger("ResourceList");

  Element parent;
  ResourceService service = new ResourceService();

  ResourceDeleteCellFactory(this.parent);

  @override
  Element createCell(CellContext context) {
    ResourceDeleteButton element = new Element.tag("resource-delete-button");
    var uri = service.extractSelfUri(context.row);
    element.uri = uri;
    element.onClick.listen((MouseEvent e) => deleteResourceWithConfirmDialog(e, uri));
    return element;
  }

  deleteResourceWithConfirmDialog(MouseEvent e, uri) {
    log.finer("Mouse clicked on ${e.target} with uri ${uri}");
    PaperActionDialog dialog = parent.shadowRoot.querySelector("#deletion-confirmation-dialog");
    PaperButton okButton = parent.shadowRoot.querySelector("#deletion-confirmation-dialog-ok");
    okButton.onClick.listen((MouseEvent e) => service.delete(uri));
    dialog.open();
  }
}

class ManyToOneCellFactory extends CellFactory {
  final Logger log = new Logger("ResourceList");

  Element parent;
  RestResourceProperty property;
  ConversionService conversionService = new ConversionService();
  ResourceService resourceService = new ResourceService();

  ManyToOneCellFactory(this.parent, this.property);

  @override
  Element createCell(CellContext context) {
    log.finer("Create cell for property ${property.name}");
    DivElement element = new DivElement();
    resourceService.get(resourceService.extractLinkUri(property.name, context.row)).
    then((Map entity) => element.innerHtml = conversionService.convertToLabel(property, entity));
    return element;
  }
}




