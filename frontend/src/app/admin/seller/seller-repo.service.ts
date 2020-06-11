import {Injectable, OnInit} from "@angular/core"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {DefaultRepo} from "../grid-common/default_repo"

export class PlainSeller{
  id: number
  name: string
  salesPointId: number
  salary: number
}

const SAVE_MUTATION = gql`
  mutation SavePlainSellers($values: [PlainSellerInput!]!){
    savePlainSellers(values: $values){
      id
      name
      salesPointId
      salary
    }
  }
`

const GET_PAGE = gql`
  query PlainSellersPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    plainSellersPage(filter: $filter, pageRequest: $pageRequest){
      values
      pageInfo{
        pageSize
      }
    }
  }
`

const DELETE_MUTATION = gql`
  mutation DeletePlainSellers($ids: [Long!]!){
    deletePlainSellers(ids: $ids)
  }
`

@Injectable({
  providedIn: "root",
})
export class PlainSellerRepo extends DefaultRepo<PlainSeller> {

  constructor(apollo: Apollo) {
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "plainSellersPage")
  }

}
