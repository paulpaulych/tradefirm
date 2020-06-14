import {Component} from "@angular/core"
import {GridProperties} from "../grid-common/grid_properties"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service"

@Component({
  selector: "app-admin-application-product",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class ApplicationProductComponent extends GridBaseComponent<any> {

  constructor(repo: CommonRepoService, dialog: MatDialog) {
    const properties = new GridProperties()
    properties.title = "Заявка-продукт"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД заявки",
        field: "applicationId",
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
      }
    ]
    super("application_product", repo, properties, dialog)
  }

}
