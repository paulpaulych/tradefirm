import {Injectable, OnInit} from "@angular/core"
import {SalesPoint} from "./sales-point"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {DefaultRepo} from "../grid-common/default_repo"

const SAVE_MUTATION = gql`
  mutation SaveSalesPoints($values: [PlainSalesPointInput!]!){
    savePlainSalesPoints(values: $values){
      id
      type
    }
  }
`

const DELETE_MUTATION = gql`
  mutation DeleteSalesPoints($ids: [Long!]!){
    deletePlainSalesPoints(ids: $ids)
  }
`

const GET_PAGE = gql`
  query SalesPointPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    plainSalesPointsPage(filter: $filter, pageRequest: $pageRequest){
      values
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
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "plainSalesPointsPage")
  }

}


