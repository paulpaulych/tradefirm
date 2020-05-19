import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {ProductsComponent} from './admin/products/products.component';
import {AuthGuard} from './security/auth-guard.service';
import {LoginComponent} from './security/login/login.component';
import {SalesPointsComponent} from './admin/sales-point/sales-points.component';
import {AnalyticsComponent} from './admin/analytics/analytics.component';

const routes = [
  {
    path: "login",
    component: LoginComponent
  },

  {
    path: "analytics",
    component: AnalyticsComponent,
    canActivate: [AuthGuard]
  },

  {
    path: "products",
    component: ProductsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "salesPoints",
    component: SalesPointsComponent,
    canActivate: [AuthGuard]
  }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
