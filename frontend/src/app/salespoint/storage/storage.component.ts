import { Component, OnInit } from '@angular/core';
import {Apollo} from "apollo-angular";
import gql from "graphql-tag";
import {showErrorMessage} from "../../admin/grid-common/insert-grid";
import {AllCommunityModules} from "@ag-grid-community/all-modules";

const QUERY = gql`
  query{
      salesPoint{
          id
          storageItems{
              product{
                  id
                  name
              }
              count
              price
          }
      }
  }
`

@Component({
  selector: 'app-storage',
  templateUrl: './storage.component.html',
  styleUrls: ['./storage.component.css']
})
export class StorageComponent implements OnInit {
  gridOptions
  defaultColDef = {
    editable: false,
    sortable: false
  };
  columnDefs = [{
    headerName: 'ИД Продукта',
    field: 'productId'
  },
  {
    headerName: 'Наименование продукта',
    field: 'productName'
  },
  {
    headerName: 'Остаток(ед.)',
    field: 'count'
  },
  {
    headerName: 'Цена(р.)',
    field: 'price'
  }];
  gridApi
  // modules = AllCommunityModules

  constructor(private apollo: Apollo) {}

  onGridReady(param){
    this.gridApi = param.api
    this.queryData()
      .subscribe({
        next: ({data, loading, errors}) => {
          if (data) {
            console.log(`got: ${JSON.stringify(data)}`)
            const preparedData = data["salesPoint"]["storageItems"].map(raw => {
                return {
                  productId: raw.product.id,
                  productName: raw.product.name,
                  count: raw.count,
                  price: raw.price
                }
              }
            )
            this.gridApi.setRowData(preparedData)
          }
          if (errors) {
            showErrorMessage(errors)
          }
        }
      })
  }

  ngOnInit(): void {

  }

  queryData(){
    return this.apollo.watchQuery({
      query: QUERY
    })
    .valueChanges
  }

}
