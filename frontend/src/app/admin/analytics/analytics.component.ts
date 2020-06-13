import { Component, OnInit } from "@angular/core"
import {AnalyticsQuery} from "./analytics_query"
import {ANALYTICS_QUERY_LIST} from "./queries"
import {MatDialog} from "@angular/material/dialog"
import {AnalyticsQueryDialogComponent} from "./analytics-query-dialog/analytics-query-dialog.component";

@Component({
  selector: "app-analytics",
  templateUrl: "./analytics.component.html",
  styleUrls: ["./analytics.component.css"]
})
export class AnalyticsComponent implements OnInit {

  queries: AnalyticsQuery[] = ANALYTICS_QUERY_LIST

  constructor(
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {}

  openQueryDialog(newQueryId: string) {
    const query = this.queries.find((q) => q.id === newQueryId)
    this.dialog.open(AnalyticsQueryDialogComponent, { width: "80%", data: query})
  }

}
