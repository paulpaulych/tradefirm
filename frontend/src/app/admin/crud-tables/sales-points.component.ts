import {Component} from "@angular/core"
import {CrudGridComponent} from "./common/crud-grid.component"
import {GridProperties, NumberParser} from "./common/grid_properties"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

class SalesPoint {
  id: number
  name: string
  areaId: number
}

@Component({
    selector: "app-sales-points",
  templateUrl: "./common/crud-grid.component.html"
})
export class SalesPointsComponent extends CrudGridComponent<SalesPoint> {

    constructor(repo: CrudRepo, dialog: MatDialog) {
        const properties = new GridProperties()
        properties.title = "Точки продаж"
        properties.columnDefs = [
            {
                headerName: "Id",
                field: "id",
                editable: false,
                filter: "agNumberColumnFilter"
            },
            {
                headerName: "Type",
                field: "type",
                filter: "agTextColumnFilter"
            },
            {
                headerName: "AreaId",
                field: "areaId",
                valueParser: NumberParser,
                filter: "agNumberColumnFilter"
            }
        ]
        super("salesPoint", repo, properties, dialog)
    }

}
