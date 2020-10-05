import {ApolloQueryResult} from "apollo-client"

export function unwrapPage<T>(res: ApolloQueryResult<Page<any>>, queryName: string): Page<T>{
  const page = res.data[queryName]
  return {
    values: page.values,
    pageInfo: page.pageInfo
  }
}

export class PageRequest{
  constructor(
    readonly pageNumber: number,
    readonly pageSize: number,
    readonly sorts: Sort[]) {}
}

export class Sort{
  constructor(
    readonly field: string,
    readonly order: string) {}
}

export enum Order {
  ASC, DESC
}

export class PageInfo {
  pageSize: number
}

export class Page<T>{
  values: T[]
  pageInfo: PageInfo
}
