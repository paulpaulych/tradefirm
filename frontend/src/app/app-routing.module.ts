import { NgModule } from "@angular/core"
import { RouterModule } from "@angular/router"
import {ProductsComponent} from "./admin/products/products.component"
import {AdminAuthGuard, AuthGuard} from "./security/auth-guard.service"
import {LoginComponent} from "./security/login/login.component"
import {SalesPointsComponent} from "./admin/sales-point/sales-points.component"
import {AnalyticsComponent} from "./admin/analytics/analytics.component"
import {TopbarComponent} from "./topbar/topbar.component"
import {WelcomeComponent} from "./welcome/welcome.component"
import {StorageComponent} from "./salespoint/storage/storage.component"
import {CustomersComponent} from "./salespoint/customers/customers.component"
import {SalesComponent} from "./salespoint/sales/sales.component"
import {ApplicationsComponent} from "./salespoint/applications/applications.component"
import {DeliveryComponent} from "./salespoint/delivery/delivery.component"
import {SaleComponent} from "./admin/sale/sale.component";
import {SellerComponent} from "./admin/seller/seller.component";
import {CustomerComponent} from "./admin/customer/customer.component";

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
