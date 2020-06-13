import { Component, OnInit } from "@angular/core"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {GridProperties} from "../grid-common/grid_properties"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service";

class PlainSeller{
  id: number
  name: string
  salesPointId: number
  salary: number
}

@Component({
  selector: "app-sale",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class SellerComponent extends GridBaseComponent<PlainSeller> {

  constructor(repo: CommonRepoService, dialog: MatDialog) {
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
