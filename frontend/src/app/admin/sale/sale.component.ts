import { Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {PlainSaleRepo, PlainSale} from "./sale-repo.service"
import {PlainCustomerRepo} from "../customer/customer-repo.service";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: "app-sale",
  templateUrl: "../grid-common/grid-common.component.html",
  styleUrls: ["../grid-common/grid_common.component.css"]
})
export class SaleComponent extends GridBaseComponent<PlainSale> {

  constructor(repo: PlainSaleRepo, dialog: MatDialog) {
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
      },
    ]
    super(repo, properties, dialog)
  }

}
