import {PageInfo} from "../grid-common/i_repo"

export class Product {
    id: number
    name: string
}

export class ProductPage {
  values: [Product]
  pageInfo: PageInfo
}
