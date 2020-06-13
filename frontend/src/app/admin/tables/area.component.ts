import {Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service";

@Component({
  selector: "app-area",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class AreaComponent extends GridBaseComponent<any> {

  constructor(repo: CommonRepoService, dialog: MatDialog) {
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
        headerName: "Площадь(кв.м.)",
        field: "square",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Стоимость аренды",
        field: "rentPrice",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Стоимость коммунальных услуг",
        field: "municipalServicesPrice",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Количество прилавков",
        field: "stallCount",
        filter: "agNumberColumnFilter"
      }
    ]
    super("area", repo, properties, dialog)
  }

}
