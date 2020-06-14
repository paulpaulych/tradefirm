import { AllCommunityModules } from "@ag-grid-community/all-modules"
import {Component, Inject} from "@angular/core"
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog"
import {CommonRepoService} from "../../common-repo.service"

export class InsertGridProperties<T> {
  colDefs = []
  type: string
  repo: CommonRepoService
}

@Component({
  selector: "app-insert-dialog",
  templateUrl: "./insert-dialog.component.html",
  styleUrls: ["./insert-dialog.component.css"]
})
export class InsertDialogComponent<T> {
  protected gridApi

  modules = AllCommunityModules
  defaultColDef
  columnDefs

  private repo: CommonRepoService
  private type

  constructor(
    private dialogRef: MatDialogRef<InsertDialogComponent<T>>,
    @Inject(MAT_DIALOG_DATA) properties: InsertGridProperties<T>) {
    this.defaultColDef = {
      editable: true,
      sortable: true,
    }
    this.repo = properties.repo
    this.columnDefs = properties.colDefs
    this.type = properties.type
  }

  onGridReady(params) {
    this.gridApi = params.api
    this.gridApi.setRowData([{}])
  }

  addRow(){
    const data = []
    this.gridApi.forEachNode((node, index) => {
      data.push(node.data)
    })
    data.push({})
    this.gridApi.setRowData(data)
  }

  removeRow() {
    const data = []
    this.gridApi.forEachNode((node, index) => {
      data.push(node.data)
    })
    data.pop()
    this.gridApi.setRowData(data)
  }

  onInsertClicked(){
    const rawData = []
    this.gridApi.forEachNode((node, index) => {
      if (Object.keys(node.data).length !== 0){
        const cleaned = JSON.parse(JSON.stringify(node.data))
        delete cleaned.__typename
        console.log(JSON.stringify(cleaned))
        rawData.push(cleaned)
      }
    })
    const data = rawData.filter((it) => it !== {})
    if (data.length === 0){
      alert("Сначала добавьте строк для вставки")
      return
    }
    this.repo.insert(this.type, data)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe(({data}) => {
          this.gridApi.setRowData([{}])
          showDataCommittedMessage(data)
          this.closeDialog()
      })

  }

  closeDialog() {
    this.dialogRef.close()
  }

}

export function showDataCommittedMessage(data = null){
  if (data) {
    console.log(`data updated: ${JSON.stringify(data)}`)
  }
  alert("Изменения применены.")
}

export function showErrorMessage(error: any) {
  console.log(error)
  alert("Что-то пошло не так. Сохраните лог и отправьте разработчику: p.pyankov@g.nsu.ru")
}
