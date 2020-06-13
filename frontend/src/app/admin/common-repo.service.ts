import { Injectable } from "@angular/core"
import {Page, PageRequest} from "./grid-common/i_repo"
import {Filter} from "./grid-common/filter"
import {map} from "rxjs/operators"
import gql from "graphql-tag"
import {Apollo} from "apollo-angular"

@Injectable({
  providedIn: "root"
})
export class CommonRepoService{

  constructor(private apollo: Apollo) {}

  queryForPage(type: string, filter: Filter, pageRequest: PageRequest) {
    return this.apollo
      .watchQuery<Page<any>>({
        query: PLAIN_QUERY,
        variables: {
          type,
          filter,
          pageRequest
        }
      })
      .valueChanges
      .pipe(map(r => {
          console.log(`got: ${JSON.stringify(r.data)}`)
          return {
            page: r.data["plainEntitiesPage"],
            loading: r.loading
          }
        }
      ))
  }

  insert(type: string, values: any[]) {
    console.log("values to insert", values)
    return this.apollo.mutate<any>({
      mutation: INSERT_MUTATION,
      variables: {
        type,
        values
      }
    })
  }

  update(type: string, value: any){
    console.log("value to insert", value)
    return this.apollo.mutate<any>({
      mutation: UPDATE_MUTATION,
      variables: {
        type,
        value
      }
    })
  }

  delete(type: string, ids: any[]) {
    console.log(`ids to delete: ${JSON.stringify(ids)}`)
    return this.apollo.mutate({
      mutation: DELETE_MUTATION,
      variables: {
        type,
        ids }
    })
  }

}

const PLAIN_QUERY = gql`
  query queryPlainEntity($type: String!, $filter: GraphQLFilterInput, $pageRequest: PageRequestDTOInput!){
    plainEntitiesPage(type: $type, filter: $filter, pageRequestDTO: $pageRequest){
      values
      pageInfo{
        pageSize
      }
    }
  }
`

const INSERT_MUTATION = gql`
  mutation Insert($type: String!, $values: [JSON!]!){
    batchInsertPlainEntities(type: $type, values: $values)
  }
`

const UPDATE_MUTATION = gql`
  mutation Insert($type: String!, $value: JSON!){
    updatePlainEntity(type: $type, value: $value)
  }
`

const DELETE_MUTATION = gql`
  mutation Delete($type: String!, $ids: [Long!]!){
    deletePlainEntity(type: $type, ids: $ids)
  }
`
