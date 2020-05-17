import { Component, OnInit } from '@angular/core';
import {AuthService} from '../security/auth-service.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

  constructor(private authService: AuthService) {}

  ngOnInit(): void{}

  isLoggedIn() {
    return this.authService.isLoggedIn()
  }

}
