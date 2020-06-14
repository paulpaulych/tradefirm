import { Component, OnInit } from "@angular/core"
import {ApplicationRepoService} from "./application-repo.service"
import {MatDialog} from "@angular/material/dialog"
import {CreateApplicationDialogComponent} from "./create-application-dialog/create-application-dialog.component"

@Component({
  selector: "app-applications",
  templateUrl: "./applications.component.html"
})
export class ApplicationsComponent implements OnInit {

  defaultColDef = {
    flex: true,
    resizable: true,
    editable: false,
    sortable: false
  }
  columnDefs = [
    {
      headerName: "ИД Заявки",
      field: "id"
    },
    {
      headerName: "Дата",
      field: "date"
    },
    {
      headerName: "Статус обработки",
      field: "status"
    }]
  gridApi

  constructor(private applicationRepoService: ApplicationRepoService,
              private dialog: MatDialog) {}

  onGridReady(param){
    this.gridApi = param.api
    this.applicationRepoService.queryData(data => {
      this.gridApi.setRowData(data)
    })
  }

  ngOnInit(): void {}

  // openApplicationInfoDialog() {
  //
  // }

  openCreateApplicationDialog() {
    const dialogRef = this.dialog.open(CreateApplicationDialogComponent, {
      width: "60%"
    })
  }
}
