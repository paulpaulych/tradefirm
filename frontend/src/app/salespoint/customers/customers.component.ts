import { Component, OnInit } from "@angular/core"
import {CustomersRepoService} from "./customers-repo.service"



@Component({
  selector: "app-customers",
  templateUrl: "./customers.component.html"
})
export class CustomersComponent implements OnInit {
  defaultColDef = {
    flex: true,
    resizable: true,
    editable: false,
    sortable: false
  }
  columnDefs = [{
    headerName: "ИД Покупателя",
    field: "id"
  },
    {
      headerName: "Имя покупателя",
      field: "name"
    }]
  gridApi

  constructor(private customersRepoService: CustomersRepoService) {}

  onGridReady(param){
    this.gridApi = param.api
    this.customersRepoService.queryData(data => {
      this.gridApi.setRowData(data)
    })
  }

  ngOnInit(): void {

  }

}
