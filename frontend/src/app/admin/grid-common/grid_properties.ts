export class GridProperties {
    title: string
    columnDefs
}

export function NumberParser(param) {
  if (param.newValue === ""){
    return null
  }
  return Number(param.newValue)
}
