import {Product} from '../products/product';
import {ApolloQueryResult} from 'apollo-client';

export interface IRepo<T> {
    queryForAll()
    queryForPage(filters: Filter[], pageRequest: PageRequest)
    saveMutation(items: Product[])
}

export function prepareApolloResult(res: ApolloQueryResult<Page<any>>, queryName: string){
  return {
    data: res.data[queryName]['values'],
    errors: res.errors,
    loading: res.errors,
    networkStatus: res.networkStatus,
    stale: res.stale
  };
}

export class Filter {
  field: string
  sign: string
  value
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
