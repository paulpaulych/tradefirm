import {Apollo} from "apollo-angular"
import {DocumentNode} from "graphql"
import {Filter} from "./filter"
import {map} from "rxjs/operators"
import {IRepo, Page, PageRequest, unwrapPage} from "./page"

export class DefaultRepo<T> implements IRepo<T> {

    constructor(private apollo: Apollo,
                private getPageQuery: DocumentNode,
                private saveMutation: DocumentNode,
                private deleteMutation: DocumentNode,
                private queryName: string) {
    }

    queryForPage(filter: Filter, pageRequest: PageRequest) {
        return this.apollo
            .watchQuery<Page<T>>({
                query: this.getPageQuery,
                variables: {
                    filter,
                    pageRequest
                }
            })
            .valueChanges
            .pipe(map(r => {
              console.log(`got: ${JSON.stringify(r.data)}`)
              return {
                page: r.data[this.queryName],
                loading: r.loading
              }
            }
            ))
    }

    save(items: T[]) {
        console.log(`values to save: ${JSON.stringify(items)}`)
        return this.apollo.mutate<T>({
            mutation: this.saveMutation,
            variables: {
                values: items
            }
        })
    }

    delete(ids: any[]) {
        console.log(`ids to delete: ${JSON.stringify(ids)}`)
        return this.apollo.mutate({
            mutation: this.deleteMutation,
            variables: { ids }
        })
    }

}
