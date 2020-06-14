import {Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service"

@Component({
  selector: "app-admin-supplier",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class SupplierComponent extends GridBaseComponent<any> {

  constructor(repo: CommonRepoService, dialog: MatDialog) {
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
