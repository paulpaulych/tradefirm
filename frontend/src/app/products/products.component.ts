import {Component, Injectable, OnDestroy, OnInit} from '@angular/core';
import {GridCommonComponent} from '../admin/grid-common/grid-common.component';
import {IRepo} from '../admin/grid-common/IRepo';
import {Product} from './product';
import {Grid_common} from '../admin/grid-common/grid_common';
import {Apollo} from 'apollo-angular';
import gql from 'graphql-tag';
import {Observer} from 'rxjs';
import {ApolloQueryResult} from 'apollo-client';
import {FetchResult} from 'apollo-link';

const getAll = gql`
  query{
    products{
        id
        name
    }
  }
`

const saveMutation = gql`
  mutation SaveProduct($product: ProductInput!){
    saveProduct(product: $product){
      id
      name
    }
  }
`

@Injectable({
  providedIn: 'root',
})
export class ProductRepo implements IRepo<Product>, OnInit{

  constructor(private apollo: Apollo) {}

  getAll(observer: Observer<ApolloQueryResult<Product>>) {
    this.apollo
      .watchQuery<Product>({
        query: gql`
          {
            products {
              id
              name
            }
          }
        `,
      })
      .valueChanges
      .subscribe(observer);
  }

  save(item: Product, observer: Observer<FetchResult<Product>>){
    this.apollo.mutate({
      mutation: saveMutation,
      variables: {
        product: item
      }
    }).subscribe(observer);
  }

  ngOnInit(): void {

  }

  update(item: Product) {
    console.log(`data changed: ${item}`)
  }

}


@Component({
  selector: 'app-products',
  templateUrl: '../admin/grid-common/admin-grid.component.html',
  styleUrls: ['../admin/grid-common/grid_common.component.css']
})
export class ProductsComponent extends GridCommonComponent<Product> implements OnInit{

  constructor(repo: ProductRepo) {
    const properties = new Grid_common()
    properties.title = 'Products'
    properties.columnDefs = [
      {
        headerName: 'Id',
        field: 'id',
        editable: false,
        sortable: true
      },
      {
        headerName: 'Name',
        field: 'name',
        editable: true,
        sortable: true
      },
    ]
    super(repo, properties)
  }

  ngOnInit(){

  }

}

