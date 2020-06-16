import { Component, OnInit } from "@angular/core"
import {MatDialog} from "@angular/material/dialog"
import {DeliveryRepoService} from "./delivery-repo.service"
import {CreateDeliveryDialogComponent} from "./create-delivery-dialog/create-delivery-dialog.component"

@Component({
  selector: "app-delivery",
  templateUrl: "./delivery.component.html"
})
export class DeliveryComponent implements OnInit {

  defaultColDef = {
    flex: true,
    resizable: true,
    editable: false,
    sortable: false
  }
  columnDefs = [
    {
      headerName: "ИД Поставки в торговую точку",
      field: "id"
    },
    {
      headerName: "Дата",
      field: "date"
    },
    {
      headerName: "Глобальный ИД поставки",
      field: "deliveryId"
    }]
  gridApi

  constructor(private deliveryRepoService: DeliveryRepoService,
              private dialog: MatDialog) {}

  onGridReady(param){
    this.gridApi = param.api
    this.deliveryRepoService.queryAll(data => {
      this.gridApi.setRowData(data)
    })
  }

  ngOnInit(): void {}

  openCreateApplicationDialog() {
    this.dialog.open(CreateDeliveryDialogComponent, {
      width: "60%"
    })
  }
}
