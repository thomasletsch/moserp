library resource_delete;

import 'package:polymer/polymer.dart';
import 'package:core_elements/core_icon_button.dart';

@CustomTag('resource-delete-button')
class ResourceDeleteButton extends PolymerElement {

  @published String uri;
  CoreIconButton button;

  ResourceDeleteButton.created() : super.created() {
    button = new CoreIconButton();
    button.setAttribute("src", "images/ic_delete_black.png");
  }

  uriChanged() {
    shadowRoot.append(button);
  }


}


