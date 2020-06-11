import {Injectable, OnInit} from "@angular/core"
import {Product} from "./product"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {DefaultRepo} from "../grid-common/default_repo"

const SAVE_MUTATION = gql`
  mutation SaveProducts($values: [ProductInput!]!){
    savePlainProducts(values: $values){
      id
      name
    }
  }
`

const GET_PAGE = gql`
  query ProductsPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    plainProductsPage(filter: $filter, pageRequest: $pageRequest){
      values
      pageInfo{
        pageSize
      }
    }
  }
`

const DELETE_MUTATION = gql`
  mutation DeleteProducts($ids: [Long!]!){
    deletePlainProducts(ids: $ids)
  }
`

@Injectable({
  providedIn: "root",
})
export class ProductRepo extends DefaultRepo<Product>{

  constructor(apollo: Apollo) {
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "plainProductsPage")
  }

}
