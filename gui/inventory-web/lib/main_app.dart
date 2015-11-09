library inventory_web.app;

import 'dart:html';
import 'dart:js';
import 'package:polymer/polymer.dart';
import 'package:route_hierarchical/client.dart';
import 'package:logging/logging.dart';
import 'common/imports.dart';
import 'package:inventory/elements/resource_page.dart';
import 'package:inventory/common/alps/structure.dart';

/// Simple class which maps page names to paths.
class Page {
  final String name;
  final String group;
  final String path;
  final String icon;
  final bool isDefault;

  const Page(this.name, this.path, this.group, {this.icon:"", this.isDefault: false});

  bool hasIcon() => icon != null;

  String toString() => '$name';
}

/// Element representing the entire example app. There should only be one of
/// these in existence.
@CustomTag('main-app')
class MainApp extends PolymerElement {
  final Logger _log = new Logger("MainApp");

  @observable Map<String, Page> pages = {};
  @observable Map<String, List<Page>> menuStructure = {};

  @observable Page selectedPage;
  @observable RestResource resource;
  @observable String route;

  Map<String, Map<String, RestResource>> groups;
  ResourcePage content;
  final Router router = new Router(useFragment: true);

  MainApp.created() : super.created(){
    readStructure();
  }

  /// Convenience getters that return the expected types to avoid casts.
  CoreA11yKeys get keys => $['keys'];

  CoreScaffold get scaffold => $['scaffold'];

  CoreAnimatedPages get corePages => $['pages'];

  CoreMenu get menu => $['menu'];

  BodyElement get body => document.body;

  readStructure() {
    StructureReader structureReader = new StructureReader();
    structureReader.queryStructure().then((e) {
      this.groups = structureReader.groups;
      buildPages();
      buildNavigation();
    });
  }

  void buildPages() {
    Map<String, Page> tmpPages = {};
    Map<String, List<Page>> tmpMenuStructure = {};
    for (var group in groups.keys) {
      tmpMenuStructure[group] = [];
      var groupNormalized = group.replaceAll(r".", "/");
      for (var resource in groups[group].values) {
        var path = groupNormalized + "/" + resource.name;
        _log.finer("Add page ${resource.name} with path $path");
        var page = new Page(resource.name, path, resource.group);
        tmpPages[path] = page;
        tmpMenuStructure[group].add(page);
      }
    }
    pages = tmpPages;
    menuStructure = tmpMenuStructure;
  }

  buildNavigation() {
    _log.finer("buildNavigation: pages = $pages");
    // Set up the routes for all the pages.
    for (var page in pages.values) {
      router.root.addRoute(
          name: page.name, path: page.path, defaultRoute: page.isDefault,
          enter: enterRoute);
    }
    router.listen();
  }

//  void routeChanged() {
//    _log.finest("Route changed for route $route");
//    if (route is! String) return;
//    if (route.isEmpty) {
//      selectedPage = null;
//    } else {
//      _log.finest("Select page for path $route");
//      selectedPage = pages[getPathGroup()].firstWhere((page) => page.path == route);
//    }
//    router.go(selectedPage.path, {});
//  }

  /// Updates [route] whenever we enter a new route.
  void enterRoute(RouteEvent e) {
    route = e.path;
    if (route == null) {
      return;
    }
    selectedPage = pages[route];
    if (groups.containsKey(selectedPage.group) && groups[selectedPage.group].containsKey(selectedPage.name)) {
      showResource();
    } else {
      showCustomElement();
    }
  }

  void showCustomElement() {
    var elementName = route + '-page';
    var content = new Element.tag(elementName);
    _log.finer('elementName: $elementName Element: ${content.tagName}');
    if (content != null) {
      var container = $['container'];
      container.children
        ..clear()
        ..add(content);
    }
  }

  void showResource() {
    resource = groups[selectedPage.group][selectedPage.name];
    var elementName = 'resource-page';
    content = new Element.tag(elementName);
    content.resource = resource;
    content.setAttribute("id", "resource-page");
    content.setAttribute("resource", "{{resource}}");
    _log.finer('elementName: $elementName Element: ${content.tagName}');
    _log.finer('Element: ${content}');
    if (content != null) {
      var container = $['container'];
      container.children
        ..clear()
        ..add(content);
    }
  }


  /// Handler for key events.
  void keyHandler(e) {
    var detail = new JsObject.fromBrowserObject(e)['detail'];

    switch (detail['key']) {
      case 'left':
      case 'up':
        corePages.selectPrevious(false);
        return;
      case 'right':
      case 'down':
        corePages.selectNext(false);
        return;
    }

  }

  /// Cycle pages on click.
  void cyclePages(Event e, detail, sender) {
    var event = new JsObject.fromBrowserObject(e);
    // Clicks on links should not cycle pages.
    if (event['target'].localName == 'a') {
      return;
    }

    event['shiftKey'] ? sender.selectPrevious(true) : sender.selectNext(true);
  }

  /// Close the menu whenever you select an item.
  void menuItemClicked(_) {
    scaffold.closeDrawer();
  }

  void addClicked(_) {
    content.newResource();
  }

  void refreshClicked(_) {
    content.refresh();
  }
}
