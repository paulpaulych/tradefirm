import {Injectable, OnInit} from '@angular/core';
import {IRepo, Page, PageRequest, prepareApolloResult} from '../grid-common/i_repo';
import {Product} from './product';
import {Apollo} from 'apollo-angular';
import {map} from 'rxjs/operators';
import gql from "graphql-tag";
import {Filter} from "../grid-common/filter";

const GET_ALL = gql`
  query{
    products{
        id
        name
    }
  }
`

const SAVE_MUTATION = gql`
  mutation SaveProducts($values: [ProductInput!]!){
    saveProducts(values: $values){
      id
      name
    }
  }
`

const GET_PAGE = gql`
  query ProductsPages($filters: [GraphQLFilterInput!]!, $pageRequest: PageRequestDTOInput!){
    productPages(filters: $filters, pageRequest: $pageRequest){
      values{
        id
        name
      }
      pageInfo{
        pageSize
      }
    }
  }
`

@Injectable({
    providedIn: 'root',
})
export class ProductRepo implements IRepo<Product>, OnInit {

    constructor(private apollo: Apollo) {}

    queryForAll() {
        return this.apollo
            .watchQuery<Product>({
                query: GET_ALL,
            })
            .valueChanges;
    }

    queryForPage(filters: [Filter], pageRequest: PageRequest) {
        return this.apollo
            .watchQuery<Page<Product>>({
                query: GET_PAGE,
                variables: {
                    filters: filters,
                    pageRequest: pageRequest
                }
            })
            .valueChanges
            .pipe(map(r => prepareApolloResult(r, 'productPages')));
    }

    saveMutation(items: Product[]) {
        return this.apollo.mutate({
            mutation: SAVE_MUTATION,
            variables: {
                values: items
            }
        });
    }

    ngOnInit(): void {}
}
