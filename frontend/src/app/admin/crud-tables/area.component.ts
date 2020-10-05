import {Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo";

@Component({
  selector: "app-area",
  templateUrl: "./common/crud-grid.component.html"
})
export class AreaComponent extends CrudGridComponent<any> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
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
