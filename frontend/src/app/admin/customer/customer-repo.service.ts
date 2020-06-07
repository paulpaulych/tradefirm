import {Injectable} from "@angular/core"
import {Apollo} from "apollo-angular"
import gql from "graphql-tag"
import {DefaultRepo} from "../grid-common/default_repo"

export class PlainCustomer{
  id: number
  name: string
}

const SAVE_MUTATION = gql`
  mutation SavePlainPlainCustomers($values: [CustomerInput!]!){
    savePlainCustomers(values: $values){
      id
      name
    }
  }
`

const GET_PAGE = gql`
  query PlainCustomersPages($filter: GraphQLFilterInput, $pageRequest: PageRequestInput!){
    plainCustomersPage(filter: $filter, pageRequest: $pageRequest){
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
  mutation DeletePlainCustomers($ids: [Long!]!){
    deletePlainCustomers(ids: $ids)
  }
`

@Injectable({
  providedIn: "root",
})
export class PlainCustomerRepo extends DefaultRepo<PlainCustomer> {

  constructor(apollo: Apollo) {
    super(apollo, GET_PAGE, SAVE_MUTATION, DELETE_MUTATION, "plainCustomersPage")
  }

}
