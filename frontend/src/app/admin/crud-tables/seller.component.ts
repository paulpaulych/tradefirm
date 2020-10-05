import { Component, OnInit } from "@angular/core"
import {CrudGridComponent} from "./common/crud-grid.component"
import {GridProperties} from "./common/grid_properties"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo";

class PlainSeller{
  id: number
  name: string
  salesPointId: number
  salary: number
}

@Component({
  selector: "app-admin-seller",
  templateUrl: "./common/crud-grid.component.html"
})
export class SellerComponent extends CrudGridComponent<PlainSeller> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Продавцы"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Имя",
        field: "name",
        filter: "agTextColumnFilter"
      },
      {
        headerName: "ИД Торговой точки",
        field: "salesPointId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Оклад(р.)",
        field: "salary",
        filter: "agNumberColumnFilter"
      }
    ]
    super("seller", repo, properties, dialog)
  }

}
