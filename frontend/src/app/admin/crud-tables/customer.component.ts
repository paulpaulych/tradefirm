import { Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

class PlainCustomer{
  id: number
  name: string
}

@Component({
  selector: "app-sale",
  templateUrl: "./common/crud-grid.component.html"
})
export class CustomerComponent extends CrudGridComponent<PlainCustomer> {

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
        headerName: "Имя",
        field: "name",
        filter: "agTextColumnFilter"
      }
    ]
    super("customer", repo, properties, dialog)
  }

}
