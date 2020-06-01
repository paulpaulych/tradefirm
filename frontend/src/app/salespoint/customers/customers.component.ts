import { Component, OnInit } from '@angular/core';
import gql from "graphql-tag";
import {Apollo} from "apollo-angular";
import {showErrorMessage} from "../../admin/grid-common/insert-grid";

const QUERY = gql`
  query{
      customers{
        id
        name
      }
  }
`

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  defaultColDef = {
    editable: false,
    sortable: false
  };
  columnDefs = [{
    headerName: 'ИД Покупателя',
    field: 'id'
  },
    {
      headerName: 'Имя покупателя',
      field: 'name'
    }];
  gridApi

  constructor(private apollo: Apollo) {}

  onGridReady(param){
    this.gridApi = param.api
    this.queryData()
      .subscribe({
        next: ({data, loading, errors}) => {
          if (data) {
            console.log(`got: ${JSON.stringify(data)}`)
            const preparedData = data["customers"]
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
