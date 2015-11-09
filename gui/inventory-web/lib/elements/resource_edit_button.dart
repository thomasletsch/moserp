library resource_edit;

import 'package:polymer/polymer.dart';
import 'package:core_elements/core_icon_button.dart';

@CustomTag('resource-edit-button')
class ResourceEditButton extends PolymerElement {

  @published String uri;
  CoreIconButton button;

  ResourceEditButton.created() : super.created() {
    button = new CoreIconButton();
    button.setAttribute("src", "images/ic_edit_black.png");
  }

  uriChanged() {
    shadowRoot.append(button);
  }


}


