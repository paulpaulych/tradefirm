import {CellChangedEvent} from 'ag-grid-community/dist/lib/entities/rowNode';
import {IRepo, PageRequest, Sort} from './i_repo';
import {GridProperties} from './grid_properties';
import {InfiniteRowModelModule} from '@ag-grid-community/infinite-row-model';
import {IGetRowsParams} from 'ag-grid';
import {Filter, prepareFilter} from "./filter";

export class GridCommonComponent<T> {
  protected gridApi;
  protected gridColumnApi;

  loading: boolean;
  title: string
  modules = [InfiniteRowModelModule];
  defaultColDef
  columnDefs
  rowModelType

  constructor(
    protected repo: IRepo<T>, properties: GridProperties) {
    this.title = properties.title
    this.defaultColDef = {
      flex: 1,
      editable: true,
      sortable: true,
    };
    this.columnDefs = properties.columnDefs
    this.rowModelType = "infinite"
  }

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.gridApi.setSortModel()
    this.gridApi.setDatasource(this.datasource())
  }

  datasource() {
    return {
      getRows: (params: IGetRowsParams) => {
        console.log('[Datasource] - rows requested by grid: ', params);
        const pageSize = this.gridApi.paginationGetPageSize()
        console.log(`sort model: ${JSON.stringify(params.sortModel)}`)
        const sorts = params.sortModel.map((s) => {
          return new Sort(s.colId, s.sort.toUpperCase())
        })
        if (sorts.length == 0) {
          const defaultSort = new Sort(this.columnDefs[0].field, "ASC")
          sorts.push(defaultSort)
        }
        console.log(`filter model: ${JSON.stringify(params.filterModel)}`)
        const filters: Filter[] = Object.keys(params.filterModel)
            .map((column) => prepareFilter(column, params.filterModel[column]))
        const pageRequest = new PageRequest(
          params.startRow / pageSize,
          params.endRow,
          sorts)
        this.repo.queryForPage(filters, pageRequest)
          .subscribe({
            next: ({data, loading, errors}) => {
              if (data) {
                console.log(`got: ${JSON.stringify(data)}`)
                params.successCallback(data)
              }
              if (errors) {
                console.log(`errors while data fetching: ${errors}`)
                params.failCallback()
              }
              this.loading = loading
            }
          })
      },
    };
  }

  cellValueChanged(event: CellChangedEvent) {
    console.trace(`cell update: ${event.oldValue} to ${event.newValue}`)
    console.trace(event.node.data)

    const cleaned = JSON.parse(JSON.stringify(event.node.data))
    delete cleaned.__typename
    this.repo.saveMutation([cleaned])
      .subscribe({
        next: ({data, errors}) => {
          console.log(`data updated: ${JSON.stringify(data)}`)
          if (data) {
            alert("changes committed")
          }
          if (errors) {
            console.log(`errors while data fetching: ${errors}`)
          }
        },
        error: err => {
          alert(`
                Error occurred while saving changes: ${err}
                Rollbacking cell editing
              `);
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

}

