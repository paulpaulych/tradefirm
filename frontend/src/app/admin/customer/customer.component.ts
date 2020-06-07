import { Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {PlainCustomer, PlainCustomerRepo} from "./customer-repo.service"
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: "app-sale",
  templateUrl: "../grid-common/grid-common.component.html",
  styleUrls: ["../grid-common/grid_common.component.css"]
})
export class CustomerComponent extends GridBaseComponent<PlainCustomer> {

  constructor(repo: PlainCustomerRepo, dialog: MatDialog) {
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
    super(repo, properties, dialog)
  }

}
