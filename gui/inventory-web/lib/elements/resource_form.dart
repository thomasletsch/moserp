library resource_form;

import 'dart:html';
import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';
import 'package:paper_elements/paper_input.dart';
import 'package:inventory/common/alps/resource_service.dart';
import 'package:inventory/common/translation.dart';
import 'package:inventory/common/conversion.dart';
import 'package:inventory/elements/sub_resource.dart';
import 'package:paper_elements/paper_button.dart';
import 'package:paper_elements/paper_action_dialog.dart';
import 'package:inventory/common/alps/structure.dart';

@CustomTag('resource-form')
class ResourceForm extends PolymerElement {
  final Logger log = new Logger("ResourceForm");

  @published RestResource resource;
  @published Map entity = {};
  Map<String, Element> formElements = {};

  ResourceService resourceService = new ResourceService();
  ConversionService conversionService = new ConversionService();
  TranslationService translationService = new TranslationService();

  ResourceForm.created() : super.created() {
  }

  DivElement get form => $["form"];

  void entityChanged() {
    if (resource == null) {
      return;
    }
    for (RestResourceProperty property in resource.properties) {
      if (!formElements.containsKey(property.name)) {
        continue;
      }
      if (formElements[property.name] is SelectElement) {
        SelectElement element = formElements[property.name];
        element.value = conversionService.convertToValue(property, entity);
      } else if (formElements[property.name] is PaperInput) {
        PaperInput element = formElements[property.name];
        element.value = entity[property.name];
      }
    }
  }

  void resourceChanged() {
    log.fine("Form $form");
    form.children.clear();
    for (RestResourceProperty property in resource.properties) {
      if (property.readOnly) {
        continue;
      }
      log.finer("Property: $property");
      form.children.add(createLabel(property));
      if (property.type == RestResourcePropertyType.VALUE || property.type == RestResourcePropertyType.ASSOCIATION) {
        addElement(property, createDropDown(property));
      } else if (property.type == "array") {
        addElement(property, createSubResourceButton(property));
      } else {
        addElement(property, createInput(property));
      }
      form.children.add(new BRElement());
    }
    if (entity.length > 0) {
      entityChanged();
    }
  }

  void addElement(RestResourceProperty property, Element element) {
    form.children.add(element);
    formElements[property.name] = element;
  }

  Element createInput(RestResourceProperty property) {
    String label = translationService.translateDirect(property.name);
    PaperInput input = new Element.tag("paper-input");
    input.label = label;
    input.setAttribute("id", "form_field_${property.name}");
    input.onChange.listen((Event event) => entity[property.name] = input.value);
    return input;
  }

  Element createSubResourceButton(RestResourceProperty property) {
    PaperButton button = new PaperButton();
    button.setAttribute("id", "form_field_${property.name}");
    button.setInnerHtml("...");
    button.onClick.listen((MouseEvent e) => openSubResourcePopup(property));
    return button;
  }

  openSubResourcePopup(RestResourceProperty property) {
    log.finer("Mouse clicked on property $property with data ${entity[property.name]}");
    PaperActionDialog dialog = querySelector("#sub-resource-dialog");
    dialog.toggle();
//    PaperButton okButton = shadowRoot.querySelector("#sub-resource-dialog-ok");
    SubResource subResource = querySelector("#sub-resource");
    subResource.subResource = property.dependentResource;
    subResource.entities = entity[property.name];
  }


  Element createLabel(RestResourceProperty property) {
    String label = translationService.translateDirect(property.name);
    LabelElement labelElement = new LabelElement();
    labelElement.setAttribute("for", "form_field_${property.name}");
    labelElement.setInnerHtml("$label:");
    return labelElement;
  }

  Element createDropDown(RestResourceProperty property) {
    log.finer("From uri");
    SelectElement select = new SelectElement();
    select.onChange.listen((Event event) => entity[property.name] = select.value);
    select.setAttribute("id", "form_field_${property.name}");
    resourceService.getList(property.uri).then((data) => populateDropDown(property, data, select));
    return select;
  }

  populateDropDown(RestResourceProperty property, List data, SelectElement dropDown) {
    log.finer("Insert Drop Down for with data=$data into $dropDown");
    for (Map resource in data) {
      OptionElement option = new OptionElement();
      option.value = conversionService.convertToValue(property, resource);
      option.label = conversionService.convertToLabel(property, resource);
      dropDown.children.add(option);
    }
  }

}