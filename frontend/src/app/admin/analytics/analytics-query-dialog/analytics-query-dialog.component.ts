import {Component, Inject, OnInit} from "@angular/core"
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog"
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Apollo} from "apollo-angular";
import {AnalyticsQuery} from "../analytics_query";

@Component({
  selector: "app-analytics-query-dialog",
  templateUrl: "./analytics-query-dialog.component.html"
})
export class AnalyticsQueryDialogComponent implements OnInit{

  defaultColDef
  columnDefs
  rowData = []

  form: FormGroup

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AnalyticsQueryDialogComponent>,
    private apollo: Apollo,
    @Inject(MAT_DIALOG_DATA) public queryProperties: AnalyticsQuery) {
    this.defaultColDef = {
      flex: 1,
      editable: false,
      sortable: true,
    }
    this.columnDefs = queryProperties.columnDefs
    const controlsConfig = {}
    queryProperties.params.forEach(queryParam => {
      controlsConfig[queryParam.name] = [queryParam.value, Validators.required]
    })
    this.form = this.fb
      .group(controlsConfig)
  }

  ngOnInit(): void {}

  runQuery() {
    const params = this.form.value
    this.apollo.watchQuery({
      query: this.queryProperties.query,
      variables: params
    })
      .valueChanges
      .subscribe(({data}) => {
          console.log("got an|alytics query result", data)
          let result = data[this.queryProperties.id]
          if (!Array.isArray(result)){
            result = [result]
          }
          if (result.length === 0){
            alert("Нет данных по запросу")
          }
          this.rowData = result
        }
      )
  }

}
