import {Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

class PlainApplication {
  id: number
  salesPointId: number
  date: string
  new: boolean
}

@Component({
  selector: "app-area",
  templateUrl: "./common/crud-grid.component.html"
})
export class ApplicationComponent extends CrudGridComponent<PlainApplication> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Заявки на поставку"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД торговой точки",
        field: "salesPointId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Дата",
        field: "date"
      },
      {
        headerName: "В обработке",
        field: "newFlag"
      }
    ]
    super("application", repo, properties, dialog)
  }

}
