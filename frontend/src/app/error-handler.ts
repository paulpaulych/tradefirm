
export function onNetworkError(err) {
  alert("Сервер недоступен. Проверьте подключение.")
  console.log(`[NETWORK ERROR]: ${err}`)
}
