import { NgModule } from "@angular/core"
import { RouterModule } from "@angular/router"
import {ProductsComponent} from "./admin/crud-tables/products.component"
import {AdminAuthGuard, AuthGuard} from "./security/auth-guard.service"
import {LoginComponent} from "./security/login/login.component"
import {SalesPointsComponent} from "./admin/crud-tables/sales-points.component"
import {AnalyticsComponent} from "./admin/analytics/analytics.component"
import {WelcomeComponent} from "./welcome/welcome.component"
import {StorageComponent} from "./salespoint/storage/storage.component"
import {CustomersComponent} from "./salespoint/customers/customers.component"
import {SalesComponent} from "./salespoint/sales/sales.component"
import {ApplicationsComponent} from "./salespoint/applications/applications.component"
import {DeliveryComponent} from "./salespoint/delivery/delivery.component"
import {SaleComponent} from "./admin/crud-tables/sale.component"
import {SellerComponent} from "./admin/crud-tables/seller.component"
import {CustomerComponent} from "./admin/crud-tables/customer.component"
import {AreaComponent} from "./admin/crud-tables/area.component"
import {DeliveryComponent as PlainDeliveryComponent} from "./admin/crud-tables/delivery.component"
import {ApplicationComponent as PlainApplicationComponent} from "./admin/crud-tables/application.component"
import {ApplicationProductComponent} from "./admin/crud-tables/application-product.component"
import {OrderComponent} from "./admin/crud-tables/order.component"
import {OrderProductComponent} from "./admin/crud-tables/order-product.component"
import {SaleProductComponent} from "./admin/crud-tables/sale-product.component"
import {SupplierComponent} from "./admin/crud-tables/supplier.component"
import {ShopDeliveryComponent} from "./admin/crud-tables/shop_delivery.component"
import {ShopDeliveryProductComponent} from "./admin/crud-tables/shop-delivery-product.component"
import {SalesPointProductComponent} from "./admin/crud-tables/salespoint-product.component"
import {SupplierPriceComponent} from "./admin/crud-tables/supplier-price.component";
import {ADMIN_PANEL_PATH, SALES_POINT_PATH} from "./security/role-authorities.service";


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
            path: "sale_product",
            component: SaleProductComponent
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
          },
          {
            path: "delivery",
            component: PlainDeliveryComponent
          },
          {
            path: "application",
            component: PlainApplicationComponent
          },
          {
            path: "application_product",
            component: ApplicationProductComponent
          },
          {
            path: "order",
            component: OrderComponent
          },
          {
            path: "order_product",
            component: OrderProductComponent
          },
          {
            path: "supplier",
            component: SupplierComponent
          },
          {
            path: "shop_delivery",
            component: ShopDeliveryComponent
          },
          {
            path: "shop_delivery_product",
            component: ShopDeliveryProductComponent
          },
          {
            path: "storage",
            component: SalesPointProductComponent
          },
          {
            path: "supplier_price",
            component: SupplierPriceComponent
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
