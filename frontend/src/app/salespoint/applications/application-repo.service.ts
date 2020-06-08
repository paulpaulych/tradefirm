import { Injectable } from "@angular/core"
import {Apollo} from "apollo-angular"
import {showErrorMessage} from "../../admin/grid-common/insert-dialog/insert-dialog.component"
import gql from "graphql-tag"

const QUERY = gql`
  query{
    salesPoint{
      id
      applications{
        id
        date
        newFlag
      }
    }
  }
`

const ADD_MUTATION = gql`
  mutation AddApplication($items: [ApplicationItemInput!]!){
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
  providedIn: "root"
})
export class ApplicationRepoService {
  constructor(private apollo: Apollo) { }

  queryData(subscribe){
    return this.apollo.watchQuery<any>({
      query: QUERY
    })
      .valueChanges
      .subscribe(({data, loading}) => {
            console.log(`got: ${JSON.stringify(data)}`)
            const preparedData = data.salesPoint.applications
              .map((it) => {
                return {
                  id: it.id,
                  date: it.date,
                  status: Boolean(it.newFlag) === true ? "В обработке" : "Заказ сформирован"
                }
              })
            subscribe(preparedData)
      })
  }

  createApplication(items){
    return this.apollo.mutate<any>({
      mutation: ADD_MUTATION,
      variables: {
        items
      }
    })
  }
}
