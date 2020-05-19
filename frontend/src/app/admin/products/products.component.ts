import {Component, OnInit} from '@angular/core';
import {GridCommonComponent} from '../grid-common/grid-common.component';
import {Product} from './product';
import {GridProperties} from '../grid-common/grid_properties';
import {ProductRepo} from './product-repo.service';

@Component({
  selector: 'app-products',
  templateUrl: '../grid-common/grid-common.component.html',
  styleUrls: ['../grid-common/grid_common.component.css']
})
export class ProductsComponent extends GridCommonComponent<Product> implements OnInit{

  constructor(repo: ProductRepo) {
    const properties = new GridProperties()
    properties.title = 'Products'
    properties.columnDefs = [
      {
        headerName: 'Id',
        field: 'id',
        editable: false,
        filter: 'agNumberColumnFilter',
        floatingFilter: true,
        filterParams: {
          resetButton: true,
          closeOnApply: true,
        }
      },
      {
        headerName: 'Name',
        field: 'name',
        editable: true,
        filter: 'agTextColumnFilter',
        floatingFilter: true,
        filterParams: {
          resetButton: true,
          closeOnApply: true,
        }
      },
    ]
    super(repo, properties)
  }

  ngOnInit(){

  }

}

