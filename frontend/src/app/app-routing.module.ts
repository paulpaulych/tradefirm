import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {ProductsComponent} from './admin/products/products.component';
import {AdminAuthGuard, AuthGuard} from './security/auth-guard.service';
import {LoginComponent} from './security/login/login.component';
import {SalesPointsComponent} from './admin/sales-point/sales-points.component';
import {AnalyticsComponent} from './admin/analytics/analytics.component';
import {TopbarComponent} from "./topbar/topbar.component";
import {WelcomeComponent} from "./welcome/welcome.component";

export const ADMIN_PANEL_PATH = "admin"

export const SALES_POINT_PATH = "salesPoint"

export const routes = [
  {
    path: "login",
    component: LoginComponent,
  },
  {
    path: "",
    canActivate: [AuthGuard],
    children: [
      {
        path: "",
        redirectTo: "/welcome",
        pathMatch: "full"
      },
      {
        path: "welcome",
        component: WelcomeComponent
      },
      {
        path: ADMIN_PANEL_PATH,
        canActivate: [AdminAuthGuard],
        children: [
          {
            path: "analytics",
            component: AnalyticsComponent
          },
          {
            path: "products",
            component: ProductsComponent
          },
          {
            path: "salesPoints",
            component: SalesPointsComponent
          }
        ]
      },
      {
        path: SALES_POINT_PATH,
        children: [

          // { path: "storage"},
          // { path: "sales" },
          // { path: "applications" },
          // { path: "deliveries" },
          // { path: "customers" }
        ]
      }
    ]
  },
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
