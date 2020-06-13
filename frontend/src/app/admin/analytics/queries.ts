import {AnalyticsQuery} from "./analytics_query"
import gql from "graphql-tag"

export const ANALYTICS_QUERY_LIST: AnalyticsQuery[] = [
  {
    id: "suppliersByProduct",
    name: "Поставщики которые поставляли определенный продукт хотя бы раз",
    params: [{
      readableName: "ID продукта",
      name: "productId",
      value: 2
    }],
    query: gql`query Analytics($productId: Long!){
          suppliersByProduct(productId: $productId){
            id
            companyName
          }
        }`,
    columnDefs: [
      {
        headerName: "ID поставщика",
        field: "id"
      },
      {
        headerName: "Название компании",
        field: "companyName"
      }
    ]
  },
  {
    id: "customersByProduct",
    name: "Покупатели, хотя бы раз покупавшие заданный товар",
    params: [{
      readableName: "ID продукта",
      name: "productId",
      value: 2
    }],
    query: gql`query Analytics($productId: Long!){
          customersByProduct(productId: $productId){
            id
            name
          }
        }`,
    columnDefs: [
      {
        headerName: "ID покупателя",
        field: "id"
      },
      {
        headerName: "Имя",
        field: "name"
      }
    ]
  },
  {
    id: "suppliersByProductAndVolume",
    name: "Поставщики которые поставили заданный торав в суммарном объеме больше заданного",
    params: [
      {
        readableName: "ID продукта",
        name: "productId",
        value: 2
      },
      {
        readableName: "Мин кол-во единиц товара",
        name: "volume",
        value: 0
      },

    ],
    query: gql`
      query Analytics($productId: Long!, $volume: Int!){
        suppliersByProductAndVolume(productId: $productId, volume: $volume){
          id
          companyName
          totallySupplied
        }
      }`,
    columnDefs: [
      {
        headerName: "ID постащика",
        field: "id"
      },
      {
        headerName: "Название компании",
        field: "companyName"
      },
      {
        headerName: "Всего поставлено единиц",
        field: "totallySupplied"
      }
    ]
  },
  {
    id: "customersByProductAndVolume",
    name: "Покупатели которые купили заданный товар в суммарном объеме не менее заданного",
    params: [
      {
        readableName: "ID продукта",
        name: "productId",
        value: 2
      },
      {
        readableName: "Мин кол-во единиц товара",
        name: "volume",
        value: 0
      },
    ],
    query: gql`
      query Analytics($productId: Long!, $volume: Int!){
        customersByProductAndVolume(productId: $productId, volume: $volume){
          id
          name
          totallyBought
        }
      }`,
    columnDefs: [
      {
        headerName: "ID покупателя",
        field: "id"
      },
      {
        headerName: "Имя",
        field: "name"
      },
      {
        headerName: "Всего куплено единиц",
        field: "totallyBought"
      }
    ]
  },
  {
    id: "productionBySeller",
    name: "Данные по выработке на одного продавца",
    params: [],
    query: gql`
      query Analytics{
        productionBySeller{
          value
        }
      }`,
    columnDefs: [
      {
        headerName: "Один продавец в среднем продал на столько-то рублей",
        field: "value"
      }
    ]
  },
  {
    id: "productionByGivenSeller",
    name: "Данные по выработке на конкретного продавца",
    params: [{
      readableName: "ID продавца",
      name: "sellerId",
      value: 2
    }],
    query: gql`
      query Analytics($sellerId: Long!){
        productionByGivenSeller(sellerId: $sellerId){
          value
        }
      }`,
    columnDefs: [
      {
        headerName: "Выработка",
        field: "value"
      }
    ]
  },
  {
    id: "productSoldBySalesPoint",
    name: "Кол-во единиц проданного товара по контретной торговой точке",
    params: [{
        readableName: "ID продукта",
        name: "productId",
        value: 2
      },
      {
        readableName: "ID торговой точки",
        name: "salesPointId",
        value: 5
      }],
    query: gql`
      query Analytics($productId: Long!, $salesPointId: Long!){
        productSoldBySalesPoint(productId: $productId, salesPointId: $salesPointId){
          totallySold
        }
      }`,
    columnDefs: [
      {
        headerName: "Один продавец в среднем продал на столько-то рублей",
        field: "totallySold"
      }
    ]
  }
]
