import {AuthInterceptor} from "./auth-interceptor.service"
import {AuthService} from "./auth-service.service"
import {HTTP_INTERCEPTORS, HttpClient, HttpEvent, HttpHandler, HttpRequest, HttpResponse} from "@angular/common/http"
import {Observable, of} from "rxjs"

jest.mock("./auth-service.service", () => ({
  AuthService: () => {
    return {
      isLoggedIn: jest.fn(() => true),
      getToken: jest.fn(() => "")
    }
  }
}))

describe("AuthInterceptor", () => {
  const mockedClass = AuthService as jest.Mock<AuthService>
  const interceptor = new AuthInterceptor(mockedClass())

  it("getUser() should return user", () => {
    interceptor.intercept(new HttpRequest<any>("GET", "MOCK_URL"), new NextHandlerMock())
  })
})

class NextHandlerMock implements HttpHandler {
  handle(req: HttpRequest<any>): Observable<HttpEvent<any>> {
    expect(req.headers.has("Authorization")).toEqual(true)
    return of(new HttpResponse())
  }
}
