library sub_resource;

import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';
import '../common/alps/structure.dart';
import 'flexible_table.dart';
import 'package:inventory/elements/resource_edit_button.dart';
import 'dart:html';
import 'package:inventory/elements/resource_delete_button.dart';
import 'package:paper_elements/paper_action_dialog.dart';
import 'package:inventory/common/alps/resource_service.dart';
import 'package:paper_elements/paper_button.dart';
import 'dart:async';

@CustomTag('sub-resource')
class SubResource extends PolymerElement {
  final Logger log = new Logger("SubResource");

  @published RestResource resource;
  @published RestResource subResource;
  @published List<Map> entities = toObservable([]);
  @observable List<String> columns = toObservable([]);
  @observable Map<String, CellFactory> cellFactories = toObservable({});
  @observable Map entity = toObservable({});


  SubResource.created() : super.created();

  void subResourceChanged() {
    populateColumns();
  }

  populateColumns() {
    if (subResource == null) {
      return;
    }
    log.fine("Resource: $subResource");
    log.fine("Resource properties: ${subResource.properties}");
    columns = []
      ..addAll(subResource.properties.map((RestResourceProperty property) => property.name))
      ..add("RESOURCE_EDIT")
      ..add("RESOURCE_DELETE");
    cellFactories["RESOURCE_EDIT"] = new ResourceEditCellFactory(this);
    cellFactories["RESOURCE_DELETE"] = new ResourceDeleteCellFactory(this);
  }

  void addClicked() {
    edit({});
  }

  void save() {
    log.finer("save");
    entities.add(entity);
  }

  void cancel() {
    log.finer("cancel");

  }

  void edit(Map entity) {
    this.entity = entity;
    PaperActionDialog dialog = shadowRoot.querySelector("#sub-resource-edit-dialog");
    dialog.toggle();
  }

  void delete(Map entity) {
    entities.remove(entity);
  }

}

class ResourceEditCellFactory extends CellFactory {
  final Logger log = new Logger("SubResource");

  SubResource resourcePage;

  ResourceEditCellFactory(this.resourcePage);

  @override
  Element createCell(CellContext context) {
    ResourceEditButton element = new Element.tag("resource-edit-button");
    element.onClick.listen((MouseEvent e) {
      log.fine("Mouse clicked on ${e.target} with resourcePage: $resourcePage");
      resourcePage.entity = context.row;
    });
    return element;
  }
}

class ResourceDeleteCellFactory extends CellFactory {
  final Logger log = new Logger("SubResource");

  SubResource resourcePage;

  ResourceDeleteCellFactory(this.resourcePage);

  @override
  Element createCell(CellContext context) {
    ResourceDeleteButton element = new Element.tag("resource-delete-button");
    element.onClick.listen((MouseEvent e) => resourcePage.delete(context.row));
    return element;
  }

}

class SubResourceCellFactory extends CellFactory {
  final Logger log = new Logger("ResourceList");

  Element parent;
  RestResourceProperty property;
  ResourceService service = new ResourceService();
  StreamSubscription<MouseEvent> subscription;

  SubResourceCellFactory(this.parent, this.property);

  @override
  Element createCell(CellContext context) {
    PaperButton button = new PaperButton();
    button.setInnerHtml("...");
    button.onClick.listen((MouseEvent e) => openSubResourcePopup(property, context));
    return button;
  }

  openSubResourcePopup(RestResourceProperty property, CellContext context) {
    log.finer("Mouse clicked on property $property with data ${context.cell}");
    PaperActionDialog dialog = parent.shadowRoot.querySelector("#sub-resource-dialog");
    dialog.toggle();
    PaperButton okButton = parent.shadowRoot.querySelector("#sub-resource-dialog-ok");
    var uri = service.extractSelfUri(context.row);
    subscription = okButton.onClick.listen((MouseEvent e) {
      service.put(context.row, uri);
      subscription.cancel();
    });
    SubResource subResource = parent.shadowRoot.querySelector("#sub-resource");
    subResource.subResource = property.dependentResource;
    subResource.entities = context.cell;
  }
}
