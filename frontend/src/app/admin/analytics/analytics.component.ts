import { Component, OnInit } from '@angular/core';
import gql from 'graphql-tag';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AnalyticsQuery} from './analyticsQuery';
import {ANALYTICS_QUERY_LIST} from './queries';
import {Apollo} from 'apollo-angular';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  queries: AnalyticsQuery[] = ANALYTICS_QUERY_LIST

  activeQuery: AnalyticsQuery = null
  form: FormGroup;
  defaultColDef
  columnDefs = null
  rowData

  constructor(
    private fb: FormBuilder,
    private apollo: Apollo
  ) {

    this.defaultColDef = {
      flex: 1,
      editable: false,
      sortable: true,
    };

  }

  ngOnInit(): void {}

  switchQuery(newQueryId: string) {
    const query = this.queries.find((q) => q.id == newQueryId)
    const controlsConfig = {}
    query.params.forEach(queryParam => {
      controlsConfig[queryParam.name] = [queryParam.value, Validators.required]
    })
    this.form = this.fb
      .group(controlsConfig)
    this.activeQuery = query
    this.columnDefs = null
  }

  runQuery() {
    const params = this.form.value
    this.apollo.watchQuery({
      query: this.activeQuery.query,
      variables: params
    })
      .valueChanges
      .subscribe((res)=>{
        if(res.data){
          console.log("got analytics query result: " + JSON.stringify(res.data))
          this.activateGrid(res.data[this.activeQuery.id])
        }else{
          console.log("errors while analytics result: " + JSON.stringify(res.errors))
        }
      }
     )
  }

  activateGrid(data){
    this.columnDefs = this.activeQuery.columnDefs
    this.rowData = data
  }
}
