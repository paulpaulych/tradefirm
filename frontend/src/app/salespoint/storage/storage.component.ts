import { Component, OnInit } from "@angular/core"
import {StorageRepoService} from "./storage-repo.service"

@Component({
  selector: "app-storage",
  templateUrl: "./storage.component.html"
})
export class StorageComponent implements OnInit {
  gridOptions
  defaultColDef = {
    flex: true,
    resizable: true,
    editable: false,
    sortable: false
  }
  columnDefs = [{
    headerName: "ИД Продукта",
    field: "productId"
  },
  {
    headerName: "Наименование продукта",
    field: "productName"
  },
  {
    headerName: "Остаток(ед.)",
    field: "count"
  },
  {
    headerName: "Цена(р.)",
    field: "price"
  }]
  gridApi

  constructor(private storageRepoService: StorageRepoService) {}

  onGridReady(param){
    this.gridApi = param.api
    this.storageRepoService.queryData((data) => {
      this.gridApi.setRowData(data)
    })
  }

  ngOnInit(): void {

  }

}
