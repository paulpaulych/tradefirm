import { Injectable } from "@angular/core"
import gql from "graphql-tag"
import {Apollo} from "apollo-angular"

const QUERY = gql`
  query{
      customers{
        id
        name
      }
  }
`

const ADD_MUTATION = gql`
  mutation AddCustomer($customerName: String!){
      addCustomer(name: $customerName){
        id
        name
      }
  }
`

@Injectable({
  providedIn: "root"
})
export class CustomersRepoService {

  constructor(private apollo: Apollo) { }

  queryData(subscribe){
    return this.apollo.watchQuery<any>({
      query: QUERY
    })
      .valueChanges
      .subscribe(({data}) => {
          console.log(`got: ${JSON.stringify(data)}`)
          const preparedData = data.customers
          subscribe(preparedData)
      })
  }

  addCustomer(name: string){
    return this.apollo.mutate<any>({
      mutation: ADD_MUTATION,
      variables: {
        customerName: name
      }
    })
  }
}
