import { Injectable } from "@angular/core"
import {Apollo} from "apollo-angular"
import {showErrorMessage} from "../../admin/grid-common/insert-dialog/insert-dialog.component"
import gql from "graphql-tag"

const QUERY = gql`
  query{
    salesPoint{
      id
      shopDeliveries{
            id
            date
            delivery{
                id
            }
        }
    }
  }
`

const ADD_MUTATION = gql`
  mutation AddCustomer($deliveryId: Long!, $items: [ShopDeliveryItemInput!]!){
    createShopDelivery(deliveryId: $deliveryId, items: $items){
        id
        items{
            product{
                id
                name
            }
            count
        }
        delivery{
            id
        }
        date
    }
  }
`

@Injectable({
  providedIn: "root"
})
export class DeliveryRepoService {
  constructor(private apollo: Apollo) { }

  queryAll(subscribe){
    return this.apollo.watchQuery<any>({
      query: QUERY
    })
      .valueChanges
      .subscribe((data) => {
        console.log(`got: ${JSON.stringify(data)}`)
        const preparedData = data.data.salesPoint.shopDeliveries
          .map((it) => {
            return {
              id: it.id,
              date: it.date,
              deliveryId: it.delivery.id
            }
          })
        subscribe(preparedData)
      })
  }

  createDelivery(deliveryId, items){
    return this.apollo.mutate<any>({
      mutation: ADD_MUTATION,
      variables: {
        deliveryId,
        items
      }
    })
  }
}
