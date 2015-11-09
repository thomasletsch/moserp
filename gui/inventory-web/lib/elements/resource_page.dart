import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';
import 'package:paper_elements/paper_action_dialog.dart';
import 'package:inventory/common/alps/structure.dart';
import 'package:inventory/common/alps/resource_service.dart';
import 'package:inventory/elements/resource_list.dart';

@CustomTag('resource-page')
class ResourcePage extends PolymerElement {
  final Logger log = new Logger("ResourcePage");

  @published RestResource resource;
  @observable ResourcePage page;
  @published Map entity = {};

  ResourceService service = new ResourceService();

  ResourcePage.created() : super.created() {
    page = this;
  }


  void newResource() {
    this.entity = {};
    openDialog();
  }

  void editResource(Map entity) {
    this.entity = entity;
    openDialog();
  }

  void save() {
    log.finer("save");
    List<String> keys = entity.keys.toList();
    keys.forEach((key) {
      if (entity[key] == "") {
        entity.remove(key);
      }
    });
    String selfUri = service.extractSelfUri(entity);
    if (selfUri == null) {
      service.post(entity, resource.uri).then((_) => refresh());
    } else {
      service.put(entity, selfUri).then((_) => refresh());
    }
    closeDialog();
  }

  void cancel() {
    log.finer("cancel");
    entity = {};
    closeDialog();
    refresh();
  }

  void refresh() {
    ResourceList list = shadowRoot.querySelector("resource-list");
    list.resourceChanged();
  }

  void openDialog() {
    PaperActionDialog dialog = shadowRoot.querySelector("#resource-edit-dialog");
    if (dialog != null)
      dialog.open();
  }

  void closeDialog() {
    PaperActionDialog dialog = shadowRoot.querySelector("#resource-edit-dialog");
    if (dialog != null)
      dialog.close();
  }
}


