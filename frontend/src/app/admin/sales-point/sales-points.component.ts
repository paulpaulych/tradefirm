import {Component} from "@angular/core"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {SalesPoint} from "./sales-point"
import {GridProperties, NumberParser} from "../grid-common/grid_properties"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service"

@Component({
    selector: "app-sales-points",
    templateUrl: "../grid-common/grid-common.component.html",
    styleUrls: ["../grid-common/grid_common.component.css"]
})
export class SalesPointsComponent extends GridBaseComponent<SalesPoint> {

    constructor(repo: CommonRepoService, dialog: MatDialog) {
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
