import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {environment} from "../../environments/environment";

const AUTH_TOKEN = "auth_token"
const AUTH_EXPIRES_AT = "auth_exp"
const AUTH_ROLE = "auth_role"
const AUTH_USERNAME = "auth_username"

class LoginCallback {
  success: ()=>void
  error: (any)=>void
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  login(username: string, password: string, callback: LoginCallback) {
    const loginRes = this.http.post(environment.authUrl, { username, password })
    loginRes.subscribe({
      next: (data)=> {
        this.setSession(data)
        callback.success()
      },
      error: err => {
        callback.error(err)
      }
    })
  }

  private setSession(authResult) {
    localStorage.setItem(AUTH_TOKEN, authResult.token);
    localStorage.setItem(AUTH_EXPIRES_AT, authResult.expiration );
    localStorage.setItem(AUTH_ROLE, authResult.role);
    localStorage.setItem(AUTH_USERNAME, authResult.username );
  }

  logout() {
    localStorage.removeItem(AUTH_TOKEN);
    localStorage.removeItem(AUTH_EXPIRES_AT);
    localStorage.removeItem(AUTH_ROLE);
    localStorage.removeItem(AUTH_USERNAME);
    return new Observable()
  }

  public isLoggedIn() {
    const now = new Date().valueOf()
    return now < Number(this.getExpiration()) && this.getToken();
  }

  public getToken(){
    return localStorage.getItem(AUTH_TOKEN)
  }

  private getExpiration() {
    return localStorage.getItem(AUTH_EXPIRES_AT);
  }

  public getUsername(){
    return localStorage.getItem(AUTH_USERNAME)
  }

  public getRole(){
    return localStorage.getItem(AUTH_ROLE)
  }

}

