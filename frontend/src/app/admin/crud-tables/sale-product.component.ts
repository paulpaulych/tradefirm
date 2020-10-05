import {Component} from "@angular/core"
import {GridProperties} from "./common/grid_properties"
import {CrudGridComponent} from "./common/crud-grid.component"
import {MatDialog} from "@angular/material/dialog"
import {CrudRepo} from "./common/crud-repo"

@Component({
  selector: "app-admin-sale-product",
  templateUrl: "./common/crud-grid.component.html"
})
export class SaleProductComponent extends CrudGridComponent<any> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Покупка-Товар"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД покупки",
        field: "saleId",
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
    ]
    super("sale_product", repo, properties, dialog)
  }

}
