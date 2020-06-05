import {Component, Inject, OnInit} from "@angular/core"
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog"
import {Apollo} from "apollo-angular"
import {showErrorMessage} from "../../../admin/grid-common/insert-grid"
import gql from "graphql-tag"


const SALE_INFO_QUERY = gql`
  query SaleInfo($id: Long!){
    sale(id: $id){
      cartItems{
        product{
            id
            name
        }
        count
      }
      date
      seller{
        id
        name
      }
      customer{
        id
        name
      }
    }

  }
`
export interface SaleInfoDialogData{
  id
}

@Component({
  selector: "app-sales-info-dialog",
  templateUrl: "./sales-info-dialog.component.html",
  styleUrls: ["./sales-info-dialog.component.css"]
})
export class SalesInfoDialogComponent implements OnInit{

  id
  data = null

  cart

  defaultColDef = {
    editable: false,
    sortable: false
  }
  columnDefs = [
    {
      headerName: "Продукт ИД)",
      field: "id"
    },
    {
      headerName: "Наименование",
      field: "name"
    },
    {
      headerName: "Кол-во",
      field: "count"
    }
  ]

  constructor(
    public dialogRef: MatDialogRef<SalesInfoDialogComponent>,
    public apollo: Apollo,
    @Inject(MAT_DIALOG_DATA) data: SaleInfoDialogData) {
    this.id = data.id
  }

  onOkClick(): void {
    this.dialogRef.close()
  }

  ngOnInit(): void {
    this.apollo.watchQuery<any>({
      query: SALE_INFO_QUERY,
      variables: {
        id: this.id
      }
    })
      .valueChanges
      .subscribe(({data, loading, errors}) => {
          if (data) {
            console.log(`got: ${JSON.stringify(data)}`)
            this.cart = data.sale.cartItems.map((c) => {
              return {
                id: c.product.id,
                name: c.product.name,
                count: c.count
              }
            })
            this.data = data.sale
          }
      })
  }

}
