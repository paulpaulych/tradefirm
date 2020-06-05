import { Injectable } from "@angular/core"
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http"
import {Observable} from "rxjs"
import {AuthService} from "./auth-service.service"

@Injectable({
  providedIn: "root"
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.authService.isLoggedIn()) {
      return next.handle(req)
    }
    const token = this.authService.getToken()
    console.trace(`request body: ${JSON.stringify(req.body)}
                           request headers: ${JSON.stringify(req.headers)}`)
    const cloned = req.clone({
      headers: req.headers.set("Authorization",
        "Bearer " + token)
    })
    return next.handle(cloned)

  }
}
