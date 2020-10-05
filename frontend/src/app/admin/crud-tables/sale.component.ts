import { Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

@Component({
  selector: "app-sale",
  templateUrl: "./common/crud-grid.component.html"
})
export class SaleComponent extends CrudGridComponent<any> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Журнал покупок"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД Покупателя",
        field: "customerId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД Торговой точки",
        field: "salesPointId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД Продавца",
        field: "sellerId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Дата",
        field: "date"
      }
    ]
    super("sale", repo, properties, dialog)
  }

}
