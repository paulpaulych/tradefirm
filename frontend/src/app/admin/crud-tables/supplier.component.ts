import {Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

@Component({
  selector: "app-admin-supplier",
  templateUrl: "./common/crud-grid.component.html"
})
export class SupplierComponent extends CrudGridComponent<any> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Поставщики"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Компания",
        field: "companyName",
        filter: "agNumberColumnFilter"
      },
    ]
    super("supplier", repo, properties, dialog)
  }

}
