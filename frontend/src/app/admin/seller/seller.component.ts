import { Component, OnInit } from "@angular/core"
import {GridBaseComponent} from "../grid-common/grid-base.component";
import {PlainSeller, PlainSellerRepo} from "./seller-repo.service";
import {GridProperties} from "../grid-common/grid_properties";
import {PlainCustomerRepo} from "../customer/customer-repo.service";
import {MatDialog} from "@angular/material/dialog";


@Component({
  selector: "app-sale",
  templateUrl: "../grid-common/grid-common.component.html",
  styleUrls: ["../grid-common/grid_common.component.css"]
})
export class SellerComponent extends GridBaseComponent<PlainSeller> {

  constructor(repo: PlainSellerRepo, dialog: MatDialog) {
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
    super(repo, properties, dialog)
  }

}
