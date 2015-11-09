library flexible_table;

import 'package:polymer/polymer.dart';
import 'package:logging/logging.dart';
import 'dart:html';
import 'package:inventory/common/translation.dart';
import 'package:inventory/common/conversion.dart';

@CustomTag('flexible-table')
class FlexibleTable extends PolymerElement {
  final Logger log = new Logger("FlexibleTable");

  @published List<String> columns = [];
  @published List<Map<String, Object>> data = [];
  @published Map<String, CellFactory> cellFactories = {};
  @published String name = "";
  @published CellClickListener cellClickListener;

  TranslationService translationService = new TranslationService();
  ConversionService conversionService = new ConversionService();

  FlexibleTable.created() : super.created();

  dataChanged() {
    Element tableElement = shadowRoot.querySelector("#${name}_table");
    if (tableElement == null) {
      log.fine("Table not yet initialized. Skipping...");
      return;
    }
    tableElement.children.clear();
    var th = new DivElement();
    th.setAttribute("id", "${name}_header");
    th.setAttribute("class", "th");
    tableElement.append(th);
    for (var column in columns) {
      DivElement td = new DivElement();
      td.setAttribute("class", "td");
      td.appendText(translationService.translateDirect(column));
      th.append(td);
    };
    int rowIdx = 0;
    for (var row in data) {
      DivElement tr = new DivElement();
      tr.setAttribute("class", "tr");
      tr.setAttribute("id", "${name}_${rowIdx}");
      int colIdx = 0;
      for (var column in columns) {
        DivElement td = new DivElement();
        td.setAttribute("class", "td");
        td.setAttribute("id", "${name}_${rowIdx}_${colIdx}");
        if (cellFactories.containsKey(column)) {
          Element element = cellFactories[column].createCell(new CellContext(column, row, rowIdx));
          td.append(element);
        } else if (row[column] is Element) {
          Element element = row[column];
          td.append(element);
        } else {
          var cell = conversionService.convert(row[column]);
          td.appendText(cell);
        }
        tr.append(td);
        colIdx++;
      }
      tableElement.append(tr);
      rowIdx++;
    }
  }

  void cellClicked(Event event) {
    String id = event.target.id;
    log.finer("cellClicked ${id}");
    List<String> idParts = id.split("_");
    int row = int.parse(idParts[1]);
    int column = int.parse(idParts[2]);
    Element target = event.target;
    if (cellClickListener != null) {
      cellClickListener.cellClicked(row, column, target);
    }
  }
}

abstract class CellClickListener {

  void cellClicked(int row, int column, Element target);

}

abstract class CellFactory {

  Element createCell(CellContext context);

}

class CellContext {

  String column;
  Map<String, Object> row;
  int rowIdx;

  Object get cell => row[column];

  CellContext(this.column, this.row, this.rowIdx) {

  }


}


