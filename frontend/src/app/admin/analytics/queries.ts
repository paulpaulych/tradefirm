import {AnalyticsQuery} from "./analyticsQuery"
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
  }
]
