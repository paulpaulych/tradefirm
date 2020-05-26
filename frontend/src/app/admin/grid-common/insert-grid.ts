import {IRepo} from "./i_repo";
import { AllCommunityModules } from '@ag-grid-community/all-modules';

export class InsertGridProperties {
  colDefs = []
}

export class InsertGrid<T> {
  protected gridApi;
  protected gridColumnApi;

  modules = AllCommunityModules;
  defaultColDef
  columnDefs

  constructor(
    protected repo: IRepo<T>,
    protected onInsertCallback: ()=>void,
    properties: InsertGridProperties) {
    this.defaultColDef = {
      editable: true,
      sortable: true,
    };
    this.columnDefs = properties.colDefs
  }

  onGridReady(params) {
    this.gridApi = params.api
    this.gridColumnApi = params.columnApi
    this.gridApi.setRowData([{}])
  }

  addRow(){
    const data = []
    this.gridApi.forEachNode((node, index)=>{
      data.push(node.data)
    })
    data.push({})
    this.gridApi.setRowData(data);
  }

  onInsertClicked(){
    let data = []
    this.gridApi.forEachNode((node, index) =>{
      if(Object.keys(node.data).length !== 0){
        const cleaned = JSON.parse(JSON.stringify(node.data))
        delete cleaned.__typename
        console.log(JSON.stringify(cleaned))
        data.push(cleaned)
      }
    })
    if (data.length == 0){
      alert("nothing to insert")
      return
    }
    this.repo.saveMutation(data)
      .subscribe({
        next: ({data, errors}) => {
          console.log(`data updated: ${JSON.stringify(data)}`)
          if (data) {
            alert("changes committed")
            this.gridApi.setRowData([])
            this.onInsertCallback()
          }
          if (errors) {
            console.log(`errors while data fetching: ${errors}`)
          }
        },
        error: err => {
          alert(`Error occurred while saving changes: ${err}`);
        }
      });

  }

}
