import { Injectable } from "@angular/core"
import {HttpClient, HttpErrorResponse} from "@angular/common/http"
import {environment} from "../../environments/environment"
import {Observable, throwError} from "rxjs"
import {catchError} from "rxjs/operators"


const AUTH_TOKEN = "auth_token"
const AUTH_EXPIRES_AT = "auth_exp"
const AUTH_ROLE = "auth_role"
const AUTH_USERNAME = "auth_username"

class LoginCallback {
  success: () => void
  error: (err) => void
  unauthorized: () => void
}

@Injectable({
  providedIn: "root"
})
export class AuthService {

  constructor(private http: HttpClient) {}

  login(username: string, password: string, callback: LoginCallback) {
    this.http.post<any>(environment.authUrl, { username, password })
      .pipe(
        catchError((err: HttpErrorResponse, caught) => {
          if (err.status === 401){
            callback.unauthorized()
          } else {
            callback.error(err)
          }
          return throwError(err)
        })
      )
      .subscribe((data) => {
        console.log(JSON.stringify(data))
        this.setSession(data)
        callback.success()
      })

  }

  private setSession(authResult) {
    localStorage.setItem(AUTH_TOKEN, authResult.token)
    localStorage.setItem(AUTH_EXPIRES_AT, authResult.expiration )
    localStorage.setItem(AUTH_ROLE, authResult.role)
    localStorage.setItem(AUTH_USERNAME, authResult.username )
  }

  logout() {
    localStorage.removeItem(AUTH_TOKEN)
    localStorage.removeItem(AUTH_EXPIRES_AT)
    localStorage.removeItem(AUTH_ROLE)
    localStorage.removeItem(AUTH_USERNAME)
    return new Observable()
  }

  public isLoggedIn() {
    const now = new Date().valueOf()
    return now < Number(this.getExpiration()) && this.getToken()
  }

  public getToken(){
    return localStorage.getItem(AUTH_TOKEN)
  }

  private getExpiration() {
    return localStorage.getItem(AUTH_EXPIRES_AT)
  }

  public getUsername(){
    return localStorage.getItem(AUTH_USERNAME)
  }

  public getRole(){
    return localStorage.getItem(AUTH_ROLE)
  }

}

