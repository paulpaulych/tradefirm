import {CellChangedEvent} from 'ag-grid-community/dist/lib/entities/rowNode';
import {IRepo, PageRequest, Sort} from './i_repo';
import {GridProperties} from './grid_properties';
import {InfiniteRowModelModule} from '@ag-grid-community/infinite-row-model';
import {Filter, prepareFilterModel} from "./filter";
import {showErrorMessage, InsertGrid, InsertGridProperties, showDataCommittedMessage} from "./insert-grid";
import {OnInit} from "@angular/core";

const PAGE_SIZE = 10

export class GridBaseComponent<T> implements OnInit{
  protected gridApi;
  protected gridColumnApi;
  gridOptions;
  loading: boolean;
  title: string
  modules = [InfiniteRowModelModule];
  defaultColDef
  columnDefs
  rowModelType
  rowSelection
  isDeleteButtonEnabled = false
  pageSize

  insertGrid: InsertGrid<T> = null

  constructor(
    protected repo: IRepo<T>, properties: GridProperties){
    this.title = properties.title
    this.defaultColDef = {
      flex: 1,
      editable: true,
      sortable: true,
    };
    this.columnDefs = properties.columnDefs
    this.rowModelType = "infinite"
    this.gridOptions = {
      cacheBlockSize: PAGE_SIZE
    }
    this.pageSize = PAGE_SIZE
    this.rowSelection = 'multiple'

    const insertGridProperties = new InsertGridProperties()
    let insertGridColDefs = []
    this.columnDefs
      .filter((cd) => cd.editable !== false)
      .forEach((cd)=>{
        insertGridColDefs.push({
          headerName: cd.headerName,
          field: cd.field,
          valueParser: cd.valueParser
        })
      })
    insertGridProperties.colDefs = insertGridColDefs
    const onInsertCallback = ()=>{
      //TODO: чета не работает обновление таблицы
      this.gridApi.purgeInfiniteCache(null)
      this.ngOnInit()
    }
    this.insertGrid = new InsertGrid<T>(this.repo, onInsertCallback, insertGridProperties)
  }

  ngOnInit(): void {}

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
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
        if (sorts.length == 0) {
          const defaultSort = new Sort(this.columnDefs[0].field, "ASC")
          sorts.push(defaultSort)
        }
        console.log(`filter model: ${JSON.stringify(params.filterModel)}`)
        const filter = prepareFilterModel(params.filterModel)
        console.log(`prepared filter: ${JSON.stringify(filter)}`)
        const pageRequest = new PageRequest(
          params.startRow / pageSize,
          pageSize,
          sorts)
        this.repo.queryForPage(filter, pageRequest)
          .subscribe({
            next: ({data, loading, errors}) => {
              if (data) {
                console.log(`got: ${JSON.stringify(data)}`)
                params.successCallback(data)
              }
              if (errors) {
                showErrorMessage(errors)
                params.failCallback()
              }
              this.loading = loading
            }
          })
      },
    };
  }

  cellValueChanged(event: CellChangedEvent) {
    const cleaned = JSON.parse(JSON.stringify(event.node.data))
    delete cleaned.__typename
    this.repo.saveMutation([cleaned])
      .subscribe({
        next: ({data, errors}) => {
          console.log(`data updated: ${JSON.stringify(data)}`)
          if (data) {
            showDataCommittedMessage()
          }
          if (errors) {
            showErrorMessage(errors)
          }
        },
        error: err => {
          showErrorMessage(err)
          this.rollbackCellChange(event)
        }
      });
  }

  private rollbackCellChange(event: CellChangedEvent) {
    const data = event.node.data
    data[event.column.getColDef().field] = event.oldValue
    console.log(`value to rollback: ${data}`)
    event.node.setData(data)
  }

  onSelectionChanged($event: any) {
    if(this.gridApi.getSelectedRows().length == 0){
      this.isDeleteButtonEnabled = false
      return
    }
    this.isDeleteButtonEnabled = true
  }

  onDeleteClicked() {
    const selectedRows = this.gridApi.getSelectedRows()
    const idFieldName = this.columnDefs.find((it)=>it.editable === false).field
    const ids = selectedRows.map((it)=> it[idFieldName])
    if(ids.length == 0){
      alert("Ничего не выбрано :(")
      return
    }
    this.repo.deleteMutation(ids)
      .subscribe({
        next: ({data, errors}) => {
          console.log(`data deleted: ${JSON.stringify(data)}`)
          if (data) {
            showDataCommittedMessage()
            this.gridApi.deselectAll()
            this.gridApi.purgeInfiniteCache()
          }
          if (errors) {
            showErrorMessage(errors)
          }
        },
        error: err => {
          showErrorMessage(err)
        }
      });
  }

}


