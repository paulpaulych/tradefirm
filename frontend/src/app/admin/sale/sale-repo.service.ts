import {Injectable, OnInit} from "@angular/core"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {DefaultRepo} from "../grid-common/default_repo"

export class PlainSale{
  id: number
  customerId: number
  salesPointId: number
  sellerId: number
  date: string
}

const SAVE_MUTATION = gql`
  mutation SavePlainSales($values: [PlainSaleInput!]!){
    savePlainSales(values: $values){
      id
      customerId
      salesPointId
      sellerId
      date
    }
  }
`

const GET_PAGE = gql`
  query PlainSalesPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    plainSalesPage(filter: $filter, pageRequest: $pageRequest){
      values
      pageInfo{
        pageSize
      }
    }
  }
`

const DELETE_MUTATION = gql`
  mutation DeletePlainSales($ids: [Long!]!){
    deletePlainSales(ids: $ids)
  }
`

@Injectable({
  providedIn: "root",
})
export class PlainSaleRepo extends DefaultRepo<PlainSale> {

  constructor(apollo: Apollo) {
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "plainSalesPage")
  }

}
