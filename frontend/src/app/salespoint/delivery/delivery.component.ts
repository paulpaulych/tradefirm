import { Component, OnInit } from "@angular/core"
import {ApplicationRepoService} from "../applications/application-repo.service"
import {MatDialog} from "@angular/material/dialog"
import {CreateApplicationDialogComponent} from "../applications/create-application-dialog/create-application-dialog.component"
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
      headerName: "глобальный ИД поставки",
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
    const dialogRef = this.dialog.open(CreateDeliveryDialogComponent, {
      width: "60%"
    })
  }
}
