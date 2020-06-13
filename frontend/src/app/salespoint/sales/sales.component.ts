import {Component, Inject, OnInit} from "@angular/core"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {MatDialog} from "@angular/material/dialog"
import {SalesInfoDialogComponent} from "./sales-info-dialog/sales-info-dialog.component"
import {CreateSaleDialogComponent} from "./create-sale-dialog/create-sale-dialog.component"

const SALES_QUERY = gql`
  query{
    salesPoint{
        id
        sales{
            id
            customer{
              id
            }
            seller{
              id
            }
            date
        }
    }
  }
`

@Component({
  selector: "app-sales",
  templateUrl: "./sales.component.html",
})
export class SalesComponent implements OnInit {
  defaultColDef = {
    flex: true,
    resizable: true,
    editable: false,
    sortable: false
  }
  columnDefs = [
    {
      headerName: "ИД Покупки",
      field: "id"
    },
    {
      headerName: "ИД покупателя",
      field: "customerId"
    },
    {
      headerName: "ИД продавца",
      field: "sellerId"
    },
    {
      headerName: "Дата",
      field: "date"
    }
  ]
  gridApi

  constructor(
    private apollo: Apollo,
    public dialog: MatDialog) {}

  onGridReady(param){
    this.gridApi = param.api
    this.apollo.watchQuery<any>({query: SALES_QUERY})
      .valueChanges
      .subscribe( ( {data} ) => {
          console.log()
          console.log(`got: ${JSON.stringify(data)}`)
          const preparedData = data.salesPoint.sales
            .map(raw => {
              return {
                id: raw.id,
                customerId: raw.customer && raw.customer.id,
                sellerId: raw.seller && raw.seller.id,
                date: raw.date
              }
            })
          this.gridApi.setRowData(preparedData)
      })
  }

  ngOnInit(){

  }

  openSaleInfoDialog(){
    const dialogRef = this.dialog.open(SalesInfoDialogComponent, {
      width: "50%",
      data: {id: this.gridApi.getSelectedRows()[0].id}
    })
  }

  openCreateSaleDialog() {
    const dialogRef = this.dialog.open(CreateSaleDialogComponent, {
      width: "60%"
    })
  }

}
