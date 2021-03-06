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
        headerName: "Выработка(р.)",
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
        headerName: "Продано(ед.)",
        field: "totallySold"
      }
    ]
  },
  {
    id: "sellerSalaryBySalesPoint",
    name: "Зарплаты продавцой данной торговой точки",
    params: [{
      readableName: "ID торговой точки",
      name: "salesPointId",
      value: 2
    }],
    query: gql`
      query Analytics($salesPointId: Long!){
        sellerSalaryBySalesPoint(salesPointId: $salesPointId){
          id
          salary
        }
      }`,
    columnDefs: [
      {
        headerName: "ID продавца",
        field: "id"
      },
      {
        headerName: "Оклад(р.)",
        field: "salary"
      }
    ]
  },
  {
    id: "deliveryByProductAndSupplier",
    name: "Поставки определенного товара указанным поставщиком",
    params: [{
        readableName: "ID продукта",
        name: "productId",
        value: 2
      },
      {
        readableName: "ID поставщика",
        name: "supplierId",
        value: 2
      }],
    query: gql`
      query Analytics($productId: Long!, $supplierId: Long!){
        deliveryByProductAndSupplier(productId: $productId, supplierId: $supplierId){
          id
          count
        }
      }`,
    columnDefs: [
      {
        headerName: "ID поставки",
        field: "id"
      },
      {
        headerName: "Объем товара(ед.)",
        field: "count"
      }
    ]
  },
  {
    id: "productionToSquare",
    name: "Отношение объема продаж к объему торговых площадей",
    params: [],
    query: gql`
      query Analytics{
        productionToSquare{
          salesPointId
          ratio
        }
      }`,
    columnDefs: [
      {
        headerName: "ID торговой точки",
        field: "salesPointId"
      },
      {
        headerName: "Отношение",
        field: "ratio"
      }
    ]
  },
  {
    id: "profitability",
    name: "Рентабельность торговых точек(отношение объема продаж к накладным расходам)",
    params: [],
    query: gql`
      query Analytics{
        profitability{
          salesPointId
          ratio
        }
      }`,
    columnDefs: [
      {
        headerName: "ID торговой точки",
        field: "salesPointId"
      },
      {
        headerName: "Отношение",
        field: "ratio"
      }
    ]
  },
  {
    id: "deliveriesByOrder",
    name: "Поставки по номеру заказа",
    params: [{
      readableName: "ID заказа",
      name: "orderId",
      value: 2
    }],
    query: gql`
      query Analytics($orderId: Long!){
        deliveriesByOrder(orderId: $orderId)
      }`,
    columnDefs: [{
      headerName: "ID поставки",
      valueGetter: (param) => {
        if (param.data){
          return param.data
        }
      }
    }]
  },
  {
    id: "customerByProduct",
    name: "Покупатели, которые покупали данный товар",
    params: [{
      readableName: "ID продукта",
      name: "productId",
      value: 2
    }],
    query: gql`
      query Analytics($productId: Long!){
        customerByProduct(productId: $productId)
      }`,
    columnDefs: [{
      headerName: "ID покупателя",
      valueGetter: (param) => {
        if (param.data){
          return param.data
        }
      }
    }]
  },
  {
    id: "mostActiveCustomers",
    name: "Топ покупателей по количеству покупок",
    params: [],
    query: gql`
      query Analytics{
        mostActiveCustomers{
          customerId
          salesCount
        }
      }`,
    columnDefs: [
      {
        headerName: "ID покупателя",
        field: "customerId"
      },
      {
        headerName: "Кол-во покупок",
        field: "salesCount"
      }
    ]
  },
  {
    id: "productStatBySalesPoint",
    name: "Данные о товарообороте данной торговой точки",
    params: [{
      readableName: "ID торговой точки",
      name: "salesPointId",
      value: 2
    }],
    query: gql`
      query Analytics($salesPointId: Long!){
        productStatBySalesPoint(salesPointId: $salesPointId){
          id
          totallySold
        }
      }`,
    columnDefs: [{
        headerName: "ID продукта",
        field: "id"
      },
      {
        headerName: "Всего продано",
        field: "totallySold"
      }]
  },
]
