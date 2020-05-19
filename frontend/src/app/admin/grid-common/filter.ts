import {error} from "@angular/compiler/src/util";

export class Filter {
  type: string
  field: string
  op: string
  operands: any[]
}

export function prepareFilter(column: string, filterParams: any): Filter {
    return {
        type: prepareType(filterParams.filterType),
        field: column,
        op: prepareOperator(filterParams.type),
        operands: filterParams.filter
    }
}

function prepareType(type: string): string{
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
    case "startsWith": return "STARTS_WITH"
    case "endsWith": return "STARTS_WITH"
    default: error("filter operator handler not implemented yet")
  }
}
