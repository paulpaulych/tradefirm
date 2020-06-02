import { Injectable } from '@angular/core';
import {Apollo} from "apollo-angular";
import {showErrorMessage} from "../../admin/grid-common/insert-grid";
import gql from "graphql-tag";

const QUERY = gql`
  query{
    salesPoint{
      applications{
        id
        date
        newFlag
      }
    }
  }
`

const ADD_MUTATION = gql`
  mutation AddCustomer($items: [ApplicationItemInput!]!){
    createApplication(items: $items){
      id
      date
      items{
        product{
          name
        }
        count
      }
      newFlag
    }
  }
`

@Injectable({
  providedIn: 'root'
})
export class ApplicationRepoService {
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
            const preparedData = data["salesPoint"]["applications"]
              .map((it)=>{
                return {
                  id: it.id,
                  date: it.date,
                  status: Boolean(it.newFlag) === true ? "В обработке" : "Заказ сформирован"
                }
              })
            subscribe(preparedData)
          }
          if (errors) {
            showErrorMessage(errors)
          }
        }
      })
  }

  createApplication(items){
    return this.apollo.mutate({
      mutation: ADD_MUTATION,
      variables: {
        items: items
      }
    })
  }
}
