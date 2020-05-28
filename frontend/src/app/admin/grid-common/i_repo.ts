import {Product} from '../products/product';
import {ApolloQueryResult} from 'apollo-client';
import {Filter} from "./filter";

export interface IRepo<T> {
    queryForAll()
    queryForPage(filters: Filter[], pageRequest: PageRequest)
    saveMutation(items: Product[]),
    deleteMutation(ids: any[])
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
