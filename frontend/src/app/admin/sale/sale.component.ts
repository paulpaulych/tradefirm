import { Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {PlainSaleRepo, PlainSale} from "./sale-repo.service"
import {MatDialog} from "@angular/material/dialog"
import * as moment from "moment"

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
        field: "date",
        valueSetter: (params) => {
          const date = moment(params.data, "dd-MM-yyyy HH:mm:ss")
          console.log(JSON.stringify(date))
          return date.format("MM/DD/YYYY HH:mm")
        },
        valueGetter: (params) => {
          const date = moment(params.data, "MM/DD/YYYY HH:mm")
          console.log(JSON.stringify(date))
          return date.format("dd-MM-yyyy HH:mm:ss")
        }
      },
    ]
    super(repo, properties, dialog)
  }

}
