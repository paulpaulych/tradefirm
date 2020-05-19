import {DocumentNode} from 'graphql';

export class AnalyticsQuery {
    constructor(
        readonly name: string,
        readonly params: AnalyticsQueryArg<any>[],
        readonly id: string,
        readonly query: DocumentNode,
        readonly columnDefs
    ) {}
}

export class AnalyticsQueryArg<T> {
  value: T

  constructor(
    value: T,
    readonly name: any,
    readonly readableName: any
  ) {
    this.value = value
  }
}
