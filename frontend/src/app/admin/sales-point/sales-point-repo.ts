import {Injectable, OnInit} from "@angular/core"
import {SalesPoint} from "./sales-point"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {DefaultRepo} from "../grid-common/default_repo"

const GET_ALL = gql`
  query{
    salesPoints{
        id
        type
        areaId
    }
  }
`

const SAVE_MUTATION = gql`
  mutation SaveSalesPoints($values: [PlainSalesPointInput!]!){
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
  query SalesPointPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    salesPointsPage(filter: $filter, pageRequest: $pageRequest){
      values{
        id
        type
        areaId
      }
      pageInfo{
        pageSize
      }
    }
  }
`

@Injectable({
  providedIn: "root",
})
export class SalesPointsRepo extends DefaultRepo<SalesPoint>{

  constructor(apollo: Apollo) {
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "salesPointsPage")
  }

}


