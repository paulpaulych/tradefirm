import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../security/auth-service.service";
import {RoleAuthoritiesService} from "../../security/role-authorities.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  items

  constructor(private authService: AuthService,
              private roleAuthoritiesService: RoleAuthoritiesService) {}

  ngOnInit(): void {
    console.log("nav bar activated")
    this.items = this.roleAuthoritiesService.getNavigationBarContent(this.authService.getRole())
  }

}
