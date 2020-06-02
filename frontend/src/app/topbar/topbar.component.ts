import { Component, OnInit, ViewChild } from '@angular/core';
import {AuthService} from '../security/auth-service.service';
import {NavigationComponent} from "./navigation/navigation.component";
import {UserInfoComponent} from "./userinfo/user-info.component";

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

  @ViewChild(NavigationComponent)
  private navigationComponent: NavigationComponent;

  @ViewChild(UserInfoComponent)
  private userInfoComponent: UserInfoComponent

  constructor(private authService: AuthService) {}

  ngOnInit(): void{}

  isLoggedIn() {
    return this.authService.isLoggedIn()
  }

}
