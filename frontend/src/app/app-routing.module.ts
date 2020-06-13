import { NgModule } from "@angular/core"
import { RouterModule } from "@angular/router"
import {ProductsComponent} from "./admin/tables/products.component"
import {AdminAuthGuard, AuthGuard} from "./security/auth-guard.service"
import {LoginComponent} from "./security/login/login.component"
import {SalesPointsComponent} from "./admin/tables/sales-points.component"
import {AnalyticsComponent} from "./admin/analytics/analytics.component"
import {WelcomeComponent} from "./welcome/welcome.component"
import {StorageComponent} from "./salespoint/storage/storage.component"
import {CustomersComponent} from "./salespoint/customers/customers.component"
import {SalesComponent} from "./salespoint/sales/sales.component"
import {ApplicationsComponent} from "./salespoint/applications/applications.component"
import {DeliveryComponent} from "./salespoint/delivery/delivery.component"
import {SaleComponent} from "./admin/tables/sale.component"
import {SellerComponent} from "./admin/tables/seller.component"
import {CustomerComponent} from "./admin/tables/customer.component"
import {AreaComponent} from "./admin/tables/area.component";

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
          },
          {
            path: "sales",
            component: SaleComponent
          },
          {
            path: "sellers",
            component: SellerComponent
          },
          {
            path: "customers",
            component: CustomerComponent
          },
          {
            path: "areas",
            component: AreaComponent
          }
        ]
      },
      {
        path: SALES_POINT_PATH,
        children: [
          {
            path: "storage",
            component: StorageComponent
          },
          {
            path: "customers",
            component: CustomersComponent
          },
          {
            path: "sales",
            component: SalesComponent
          },
          {
            path: "applications",
            component: ApplicationsComponent
          },
          {
            path: "deliveries",
            component: DeliveryComponent
          },

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
