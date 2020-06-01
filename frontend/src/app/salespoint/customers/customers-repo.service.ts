import { Injectable } from '@angular/core';
import gql from "graphql-tag";
import {Apollo} from "apollo-angular";
import {showErrorMessage} from "../../admin/grid-common/insert-grid";

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
  providedIn: 'root'
})
export class CustomersRepoService {

  constructor(private apollo: Apollo) { }

  queryData(subscribe){
    return this.apollo.watchQuery({
      query: QUERY
    })
      .valueChanges
      .subscribe({
        next: ({data, loading, errors}) => {
          if (data) {
            console.log(`got: ${JSON.stringify(data)}`)
            const preparedData = data["customers"]
            subscribe(preparedData)
          }
          if (errors) {
            showErrorMessage(errors)
          }
        }
      })
  }

  addCustomer(name: string){
    return this.apollo.mutate({
      mutation: ADD_MUTATION,
      variables: {
        customerName: name
      }
    })
  }
}
