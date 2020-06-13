import { Component, OnInit } from "@angular/core"
import {MatDialog} from "@angular/material/dialog"
import {DeliveryRepoService} from "./delivery-repo.service"
import {CreateDeliveryDialogComponent} from "./create-delivery-dialog/create-delivery-dialog.component"

@Component({
  selector: "app-delivery",
  templateUrl: "./delivery.component.html",
  styleUrls: ["./delivery.component.css"]
})
export class DeliveryComponent implements OnInit {

  defaultColDef = {
    editable: false,
    sortable: false,
    resizable: true
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
