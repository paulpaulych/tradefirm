import {Component, Inject, OnInit} from '@angular/core';
import {Apollo} from "apollo-angular";
import {showErrorMessage} from "../../admin/grid-common/insert-grid";
import gql from "graphql-tag";
import {MatDialog} from "@angular/material/dialog";
import {SalesInfoDialogComponent} from "./sales-info-dialog/sales-info-dialog.component";
import {CreateSaleDialogComponent} from "./create-sale-dialog/create-sale-dialog.component";

const SALES_QUERY = gql`
  query{
    salesPoint{
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
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {
  defaultColDef = {
    editable: false,
    sortable: false
  };
  columnDefs = [
    {
      headerName: 'ИД Покупки',
      field: 'id'
    },
    {
      headerName: 'ИД покупателя',
      field: 'customerId'
    },
    {
      headerName: 'ИД продавца',
      field: 'sellerId'
    },
    {
      headerName: 'Дата',
      field: 'date'
    }
  ];
  gridApi

  constructor(
    private apollo: Apollo,
    public dialog: MatDialog) {}

  onGridReady(param){
    this.gridApi = param.api
    this.queryData()
      .subscribe({
        next: ({data, loading, errors}) => {
          if (data) {
            console.log(`got: ${JSON.stringify(data)}`)
            const preparedData = data["salesPoint"]["sales"]
              .map(raw=>{
                return {
                  id: raw.id,
                  customerId: raw.customer && raw.customer.id,
                  sellerId: raw.seller && raw.seller.id,
                  date: raw.date
                }
              })
            this.gridApi.setRowData(preparedData)
          }
          if (errors) {
            showErrorMessage(errors)
          }
        }
      })
  }

  ngOnInit(){

  }

  openSaleInfoDialog(){
    console.log(`selected rows: ${JSON.stringify(this.gridApi.getSelectedRows())}`)
    const dialogRef = this.dialog.open(SalesInfoDialogComponent, {
      width: '50%',
      data: {id: this.gridApi.getSelectedRows()[0]["id"]}
    });
  }

  openCreateSaleDialog() {
    console.log(`selected rows: ${JSON.stringify(this.gridApi.getSelectedRows())}`)
    const dialogRef = this.dialog.open(CreateSaleDialogComponent, {
      width: '60%'
    });
  }

  queryData(){
    return this.apollo.watchQuery({
      query: SALES_QUERY
    })
      .valueChanges
  }
}
