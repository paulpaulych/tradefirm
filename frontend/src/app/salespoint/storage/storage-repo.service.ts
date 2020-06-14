import { Injectable } from "@angular/core"
import gql from "graphql-tag"
import {Apollo} from "apollo-angular"

const QUERY = gql`
  query{
      salesPoint{
          id
          storageItems{
              product{
                  id
                  name
              }
              count
              price
          }
      }
  }
`

@Injectable({
  providedIn: "root"
})
export class StorageRepoService {

  constructor(private apollo: Apollo) { }

  queryData(subscribe){
    this.apollo.watchQuery<any>({
      query: QUERY
    })
      .valueChanges
      .subscribe(({data}) => {
          const preparedData = data.salesPoint.storageItems.map(raw => {
              return {
                productId: raw.product.id,
                productName: raw.product.name,
                count: raw.count,
                price: raw.price
              }
            }
          )
          subscribe(preparedData)
      })
  }
}
