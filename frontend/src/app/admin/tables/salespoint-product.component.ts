import {Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service"

@Component({
  selector: "app-admin-storage",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class SalesPointProductComponent extends GridBaseComponent<any> {

  constructor(repo: CommonRepoService, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Торговая точка - Товар"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД торгововой точки",
        field: "salesPointId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД продукта",
        field: "productId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Кол-во(ед.)",
        field: "count",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Цена(р.)",
        field: "price",
        filter: "agNumberColumnFilter"
      }
    ]
    super("storage", repo, properties, dialog)
  }

}
