import {Component, OnInit} from "@angular/core"
import {GridBaseComponent} from "../grid-common/grid-base.component"
import {Product} from "./product"
import {GridProperties} from "../grid-common/grid_properties"
import {ProductRepo} from "./product-repo.service"

@Component({
  selector: "app-products",
  templateUrl: "../grid-common/grid-common.component.html",
  styleUrls: ["../grid-common/grid_common.component.css"]
})
export class ProductsComponent extends GridBaseComponent<Product> implements OnInit{

  constructor(repo: ProductRepo) {
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
    super(repo, properties)
  }

  ngOnInit(){

  }

}

