import {error} from "@angular/compiler/src/util"

export class Filter {
  type: string
  field?: string
  left?: Filter
  right?: Filter
  op: string
  operands: any[]
}

export function prepareFilterModel(filterModel): Filter {
  const columns = Object.keys(filterModel)
  if (columns.length == 0){
    return null
  }
  return columns
    .map((column) => prepareColumnFilter(column, filterModel[column]))
    .reduce((prevFilter, curFilter) => {
      return {
        type: "STRUCTURAL",
        left: prevFilter,
        right: curFilter,
        op: "AND",
        operands: []
      }
    })

}

function prepareColumnFilter(column, filterParams): Filter{
  if (filterParams.operator == "AND" || filterParams.operator == "AND"){
    return structuralFilter(column, filterParams)
  }
  return {
    type: prepareType(filterParams.filterType),
    field: column,
    op: prepareOperator(filterParams.type),
    operands: [filterParams.filter]
  }
}

function structuralFilter(column, filterParams){
  return {
    type: "STRUCTURAL",
    left: prepareColumnFilter(column, filterParams.condition1),
    right: prepareColumnFilter(column, filterParams.condition2),
    op: filterParams.operator,
    operands: []
  }
}

function prepareType(type): string{
  switch (type) {
    case "text": return "STRING"
    case "number": return "NUMBER"
    default: error("filter type handler not implemented yet")
  }
}

function prepareOperator(type: string): string{
  switch (type) {
    case "equals": return "EQUALS"
    case "contains": return "CONTAINS"
    case "notContains": return "NOT_CONTAINS"
    case "startsWith": return "STARTS_WITH"
    case "endsWith": return "ENDS_WITH"
    case "notEqual": return "NOT_EQUALS"
    case "greaterThan": return "GREATER"
    case "lessThan": return "LESS"
    case "lessThanOrEqual": return "LESS_EQUALS"
    case "greaterThanOrEqual": return "GREATER_EQUALS"
    default: error("filter operator handler not implemented yet")
  }
}
