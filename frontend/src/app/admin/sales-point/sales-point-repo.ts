import {Injectable, OnInit} from '@angular/core';
import {IRepo, Page, PageRequest, prepareApolloResult} from '../grid-common/i_repo';
import {SalesPoint} from './sales-point';
import {Apollo} from 'apollo-angular';
import gql from 'graphql-tag';
import {map} from 'rxjs/operators';
import {Filter} from "../grid-common/filter";

const GET_ALL = gql`
  query{
    salesPoints{
        id
        type
    }
  }
`

const SAVE_MUTATION = gql`
  mutation SaveSalesPoints($values: [SalesPointSaveReqInput!]!){
    saveSalesPoints(values: $values){
      id
      type
    }
  }
`

const DELETE_MUTATION = gql`
  mutation DeleteSalesPoints($ids: [Long!]!){
    deleteSalesPoints(ids: $ids)
  }
`

const GET_PAGE = gql`
  query SalesPointPages($filters: [GraphQLFilterInput!]!, $pageRequest: PageRequestDTOInput!){
    salesPointsPage(filters: $filters, pageRequest: $pageRequest){
      values{
        id
        type
        area{
          id
        }
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
export class SalesPointsRepo implements IRepo<SalesPoint>, OnInit{

  constructor(private apollo: Apollo) {}

  queryForAll() {
    return this.apollo
      .watchQuery<SalesPoint>({
        query: GET_ALL,
      })
      .valueChanges
  }

  queryForPage(filters: Filter[], pageRequest: PageRequest) {
    return this.apollo
      .watchQuery<Page<SalesPoint>>({
        query: GET_PAGE,
        variables: {
          filters: filters,
          pageRequest: pageRequest
        }
      })
      .valueChanges
      .pipe(map(r => prepareApolloResult(r, 'salesPointsPage')))
      .pipe(map((r) => {
         r.data.forEach( salesPoint =>{
            if(salesPoint.area){
              salesPoint.areaId = salesPoint.area.id
            }
            delete salesPoint.area
          }
        )
        console.log(`showing: ${JSON.stringify(r)}`)
        return r
      }))
  }


  saveMutation(items: SalesPoint[]){
    delete items[0]['area']
    return this.apollo.mutate({
      mutation: SAVE_MUTATION,
      variables: {
        values: items
      }
    });
  }

  deleteMutation(ids) {
    console.log(`to delete: ${JSON.stringify(ids)}`)
    return this.apollo.mutate({
      mutation: DELETE_MUTATION,
      variables: {
        ids: ids
      }
    });
  }

  ngOnInit(): void {

  }

}


