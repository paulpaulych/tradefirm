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
  selector: "app-admin-supplier-price",
  templateUrl: "./common/crud-grid.component.html"
})
export class SupplierPriceComponent extends CrudGridComponent<PlainApplication> {

  constructor(repo: CrudRepo, dialog: MatDialog) {
    const properties = new GridProperties()

    properties.title = "Прайс поставщиков"
    properties.columnDefs = [
      {
        headerName: "ИД",
        field: "id",
        editable: false,
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД поставщика",
        field: "supplierId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "ИД продукта",
        field: "productId",
        filter: "agNumberColumnFilter"
      },
      {
        headerName: "Цена(р.)",
        field: "price"
      }
    ]
    super("supplier_price", repo, properties, dialog)
  }

}
