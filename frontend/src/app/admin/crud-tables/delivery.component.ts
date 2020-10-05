import {Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

@Component({
  selector: "app-area",
  templateUrl: "./common/crud-grid.component.html"
})
export class DeliveryComponent extends CrudGridComponent<any> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Поставки на склад"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД заказа",
        field: "orderId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД поставщика",
        field: "supplierId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Дата",
        field: "date"
      }
    ]
    super("delivery", repo, properties, dialog)
  }

}
