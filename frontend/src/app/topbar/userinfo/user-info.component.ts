import { Component, OnInit } from "@angular/core"
import {AuthService} from "../../security/auth-service.service"
import {Router} from "@angular/router"

@Component({
  selector: "app-user-info",
  templateUrl: "./user-info.component.html",
  styleUrls: ["./user-info.component.css"]
})
export class UserInfoComponent implements OnInit {

  username: string

  constructor(private authService: AuthService,
              private router: Router) {}

  logout(){
    this.authService.logout()
    this.router.navigateByUrl("/login")
  }

  ngOnInit(): void {
    this.username = this.authService.getUsername()
  }
}
