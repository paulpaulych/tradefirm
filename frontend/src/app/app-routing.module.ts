import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {ProductsComponent} from './admin/products/products.component';
import {AuthGuard} from './security/auth-guard.service';
import {LoginComponent} from './security/login/login.component';
import {SalesPointsComponent} from './admin/sales-point/sales-points.component';

const routes = [
  {
    path: "products",
    component: ProductsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "salesPoints",
    component: SalesPointsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "login",
    component: LoginComponent
  }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
