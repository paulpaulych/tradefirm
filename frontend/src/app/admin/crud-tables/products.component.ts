import {Component} from "@angular/core"
import {CrudGridComponent} from "./common/crud-grid.component"
import {GridProperties} from "./common/grid_properties"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

export class Product {
  id: number
  name: string
}

@Component({
  selector: "app-products",
  templateUrl: "./common/crud-grid.component.html"
})
export class ProductsComponent extends CrudGridComponent<Product>{

  constructor(repo: CrudRepo, dialog: MatDialog) {
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

