import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../auth-service.service';
import {Router} from '@angular/router';
import {RoleAuthoritiesService} from "../role-authorities.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private roleAuthoritiesService: RoleAuthoritiesService,
              private router: Router) {

    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    const val = this.form.value;

    if (val.username && val.password) {
      this.authService.login(
        val.username,
        val.password,
        {
          success: () => {
            console.log("User is logged in");
            const role = this.authService.getRole()
            const welcomePagePath = this.roleAuthoritiesService.welcomePage(role)
            console.log("welcome: "+ welcomePagePath)
            this.router.navigateByUrl(welcomePagePath);
          },
          error: () => {
            alert("incorrect username or password")
            this.router.navigateByUrl("/login")
          }
        })
    }
  }
}
