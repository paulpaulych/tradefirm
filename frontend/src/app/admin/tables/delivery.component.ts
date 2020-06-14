import {Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service"

@Component({
  selector: "app-area",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class DeliveryComponent extends GridBaseComponent<any> {

  constructor(repo: CommonRepoService, dialog: MatDialog) {
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
