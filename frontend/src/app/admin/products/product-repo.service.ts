import {Injectable, OnInit} from "@angular/core"
import {IRepo, Page, PageRequest, unwrapPage} from "../grid-common/i_repo"
import {Product} from "./product"
import {Apollo} from "apollo-angular"
import {map} from "rxjs/operators"
import gql from "graphql-tag"
import {Filter} from "../grid-common/filter"
import {DefaultRepo} from "../grid-common/default_repo";

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
  query ProductsPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    productsPage(filter: $filter, pageRequest: $pageRequest){
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

const DELETE_MUTATION = gql`
  mutation DeleteProducts($ids: [Long!]!){
    deleteProducts(ids: $ids)
  }
`

@Injectable({
  providedIn: "root",
})
export class ProductRepo extends DefaultRepo<Product>{

  constructor(apollo: Apollo) {
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "productsPage")
  }

}
