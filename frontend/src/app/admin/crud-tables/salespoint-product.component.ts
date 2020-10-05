import {Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

@Component({
  selector: "app-admin-storage",
  templateUrl: "./common/crud-grid.component.html"
})
export class SalesPointProductComponent extends CrudGridComponent<any> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Торговая точка - Товар"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД торгововой точки",
        field: "salesPointId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД продукта",
        field: "productId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Кол-во(ед.)",
        field: "count",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Цена(р.)",
        field: "price",
        filter: "agNumberColumnFilter"
      }
    ]
    super("storage", repo, properties, dialog)
  }

}
