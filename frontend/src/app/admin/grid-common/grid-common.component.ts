import {CellChangedEvent} from 'ag-grid-community/dist/lib/entities/rowNode';
import {IRepo} from './IRepo';
import {Grid_common} from './grid_common';

export class GridCommonComponent<T>{
    protected gridApi;
    protected gridColumnApi;
    protected repo: IRepo<T>;
    loading: boolean;
    title: string
    rowData: T[];
    defaultColDef
    columnDefs;
    constructor(repository: IRepo<T>, properties: Grid_common) {
        this.repo = repository;
        this.title = properties.title
        this.defaultColDef = {
          flex: 1,
          editable: true,
          sortable: true,
        };
        this.columnDefs = properties.columnDefs
    }
    onGridReady(params) {
        this.gridApi = params.api;
        this.gridColumnApi = params.columnApi;
        this.repo.getAll( {
          next: ({data, loading, errors}) => {
            if(data) {
              this.gridApi.setRowData(data && data['products'])
            }
            if(errors){
              console.log(`errors while data fetching: ${errors}`)
            }
            this.loading = loading
          }
        });
    }
    cellValueChanged(event: CellChangedEvent) {
        console.trace(`cell update: ${event.oldValue} to ${event.newValue}`)
        console.trace(event.node.data)

        const cleaned = JSON.parse(JSON.stringify(event.node.data))
        delete cleaned.__typename
        this.repo.save(cleaned, {
          next: ({data, errors}) => {
            console.log(`data updated: ${data}`)
            if(data) {
              alert("changes committed")
            }
            if(errors){
              console.log(`errors while data fetching: ${errors}`)
            }
          },
          error: err => {
            alert(`
              Error occurred while saving changes: ${err}
              Rollbacking cell editing
            `)
            this.rollbackCellChange(event)
          }
        });
    }

    private rollbackCellChange(event: CellChangedEvent){
      const data = event.node.data
      data[event.column.getColDef().field] = event.oldValue
      console.log(`value to rollback: ${data}`)
      event.node.setData(data)
    }

}
