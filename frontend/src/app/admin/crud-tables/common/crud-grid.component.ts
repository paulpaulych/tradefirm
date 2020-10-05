import {CellChangedEvent} from "ag-grid-community/dist/lib/entities/rowNode"
import {PageRequest, Sort} from "./page"
import {GridProperties} from "./grid_properties"
import {InfiniteRowModelModule} from "@ag-grid-community/infinite-row-model"
import {prepareFilterModel} from "./filter"
import {InsertDialogComponent, InsertGridProperties, showDataCommittedMessage} from "./insert-dialog/insert-dialog.component"
import {OnInit} from "@angular/core"
import {MatDialog} from "@angular/material/dialog"
import {catchError} from "rxjs/operators"
import {of} from "rxjs"
import {CrudRepo} from "./crud-repo"

const PAGE_SIZE = 10

export class CrudGridComponent<T> implements OnInit{
  protected gridApi
  protected gridColumnApi
  gridOptions
  loading: boolean
  title: string
  modules = [InfiniteRowModelModule]
  defaultColDef
  columnDefs
  rowModelType
  rowSelection
  isDeleteButtonEnabled = false
  pageSize
  components

  constructor(
    protected type: string,
    protected repo: CrudRepo,
    properties: GridProperties,
    private dialog: MatDialog){
    this.title = properties.title
    this.defaultColDef = {
      flex: 1,
      editable: true,
      sortable: true,
      resizable: true,
      floatingFilter: true,
      filterParams: {
      resetButton: true,
        closeOnApply: true,
      }
    }
    this.columnDefs = properties.columnDefs
    this.rowModelType = "infinite"
    this.gridOptions = {
      cacheBlockSize: PAGE_SIZE
    }
    this.pageSize = PAGE_SIZE
    this.rowSelection = "multiple"


    const onInsertCallback = () => {
      this.gridApi.purgeInfiniteCache(null)
      this.ngOnInit()
    }
  }

  openInsertDialog(){
    const properties = new InsertGridProperties()
    const insertGridColDefs = []
    this.columnDefs
      .filter((cd) => cd.editable !== false)
      .forEach((cd) => {
        insertGridColDefs.push({
          headerName: cd.headerName,
          field: cd.field,
          valueParser: cd.valueParser
        })
      })
    properties.type = this.type
    properties.colDefs = insertGridColDefs
    properties.repo = this.repo
    this.dialog.open(InsertDialogComponent, {
      width: "80%",
      data: properties
    })
  }

  ngOnInit(): void {}

  onGridReady(params) {
    this.gridApi = params.api
    this.gridColumnApi = params.columnApi
    this.gridApi.setSortModel()
    this.gridApi.setDatasource(this.datasource())
  }

  datasource() {
    return {
      getRows: (params) => {
        const pageSize = this.gridApi.paginationGetPageSize()
        const sorts = params.sortModel.map((s) => {
          return new Sort(s.colId, s.sort.toUpperCase())
        })
        if (sorts.length === 0) {
          const defaultSort = new Sort(this.columnDefs[0].field, "ASC")
          sorts.push(defaultSort)
        }
        console.log("filter model", params.filterModel)
        const filter = prepareFilterModel(params.filterModel)
        console.log("prepared filter", filter)
        const pageRequest = new PageRequest(
          params.startRow / pageSize,
          pageSize,
          sorts)
        this.repo.queryForPage(this.type, filter, pageRequest)
          .subscribe(({ page, loading }) => {
              params.successCallback(page.values)
              this.loading = loading
            }
          )
      }
    }
  }

  updateCellValue(event: CellChangedEvent) {
    const cleaned = JSON.parse(JSON.stringify(event.node.data))
    delete cleaned.__typename
    this.repo.update(this.type, cleaned)
      .pipe(
        catchError((err, caught) => {
          if (err.graphQLErrors){
            this.rollbackCellChange(event)
            console.log("error caught", JSON.stringify(err))
          }
          return of(undefined)
        })
      )
      .subscribe((data) => {
        if (data) {
          showDataCommittedMessage(data)
        }
      })
  }

  private rollbackCellChange(event: CellChangedEvent) {
    const data = event.node.data
    data[event.column.getColDef().field] = event.oldValue
    console.log(`value to rollback: ${data}`)
    event.node.setData(data)
  }

  onSelectionChanged($event: any) {
    if (this.gridApi.getSelectedRows().length === 0){
      this.isDeleteButtonEnabled = false
      return
    }
    this.isDeleteButtonEnabled = true
  }

  deleteSelected() {
    const selectedRows = this.gridApi.getSelectedRows()
    const idFieldName = this.columnDefs.find((it) => it.editable === false).field
    const ids = selectedRows.map((it) => it[idFieldName])
    if (ids.length === 0){
      alert("Ничего не выбрано :(")
      return
    }
    this.repo.delete(this.type, ids)
      .subscribe(({data}) => {
          console.log(`data deleted: ${JSON.stringify(data)}`)
          showDataCommittedMessage()
          this.gridApi.deselectAll()
          this.gridApi.purgeInfiniteCache()
      })
  }

}
