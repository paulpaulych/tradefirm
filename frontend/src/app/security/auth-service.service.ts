import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';

const AUTH_TOKEN = "auth_token"
const AUTH_EXPIRES_AT = "auth_exp"

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  username: string
  role: string

  login(username: string, password: string) {
    const loginRes = this.http.post('http://localhost:3000/login', { username, password })
    loginRes.subscribe(
      res => this.setSession(res)
    )
    return loginRes
  }

  private setSession(authResult) {
    localStorage.setItem(AUTH_TOKEN, authResult.token);
    localStorage.setItem(AUTH_EXPIRES_AT, authResult.expiration );
    this.username = authResult.username;
    this.role = authResult.role;
  }

  logout() {
    localStorage.removeItem(AUTH_TOKEN);
    localStorage.removeItem(AUTH_EXPIRES_AT);
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
}

