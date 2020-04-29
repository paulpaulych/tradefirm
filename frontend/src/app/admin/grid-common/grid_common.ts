import {NextObserver} from 'rxjs';
import {ApolloQueryResult} from 'apollo-client';
import {Product} from '../../products/product';
import {FetchResult} from 'apollo-link';

export class Grid_common {
    title: string;
    columnDefs;
}

export interface IRepo<T> {

  getAll(observer: NextObserver<ApolloQueryResult<T>>)

  save(item: Product, observer: NextObserver<FetchResult<T>>)

}

