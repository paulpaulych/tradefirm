import {Component} from "@angular/core"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {GridProperties} from "../grid-common/grid_properties"
import {MatDialog} from "@angular/material/dialog"
import {CommonRepoService} from "../common-repo.service"

export class Product {
  id: number
  name: string
}

@Component({
  selector: "app-products",
  templateUrl: "../grid-common/grid-base.component.html",
  styleUrls: ["../grid-common/grid_base.component.css"]
})
export class ProductsComponent extends GridBaseComponent<Product>{

  constructor(repo: CommonRepoService, dialog: MatDialog) {
    const properties = new GridProperties()
    properties.title = "Products"
    properties.columnDefs = [
      {
        headerName: "Id",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Name",
        field: "name",
        filter: "agTextColumnFilter"
      },
    ]
    super("product", repo, properties, dialog)
  }

}

