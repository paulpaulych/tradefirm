import { BrowserModule } from "@angular/platform-browser"
import { NgModule } from "@angular/core"

import { AppRoutingModule } from "./app-routing.module"
import { AppComponent } from "./app.component"
import { NavigationComponent } from "./topbar/navigation/navigation.component"
import { NoopAnimationsModule } from "@angular/platform-browser/animations"
import {MatMenuModule} from "@angular/material/menu"
import {MatIconModule} from "@angular/material/icon"
import {MatButtonModule} from "@angular/material/button"
import {ProductsComponent} from "./admin/crud-tables/products.component"
import {AgGridModule} from "ag-grid-angular"
import {Apollo, APOLLO_OPTIONS, ApolloModule} from "apollo-angular"

import {HttpLink, HttpLinkModule} from "apollo-angular-link-http"
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http"
import { LoginComponent } from "./security/login/login.component"
import {FormsModule, ReactiveFormsModule} from "@angular/forms"
import {AuthInterceptor} from "./security/auth-interceptor.service"
import { UserInfoComponent } from "./topbar/userinfo/user-info.component"
import { TopbarComponent } from "./topbar/topbar.component"
import {SalesPointsComponent} from "./admin/crud-tables/sales-points.component"
import { AnalyticsComponent } from "./admin/analytics/analytics.component"
import {environment} from "../environments/environment"
import { WelcomeComponent } from "./welcome/welcome.component"
import { StorageComponent } from "./salespoint/storage/storage.component"
import { CustomersComponent } from "./salespoint/customers/customers.component"
import {SalesComponent} from "./salespoint/sales/sales.component"
import {MatDialogModule} from "@angular/material/dialog"
import {SalesInfoDialogComponent} from "./salespoint/sales/sales-info-dialog/sales-info-dialog.component"
import { CreateSaleDialogComponent } from "./salespoint/sales/create-sale-dialog/create-sale-dialog.component"
import {MatFormFieldModule} from "@angular/material/form-field"
import {MatInputModule} from "@angular/material/input"
import {MatSelectModule} from "@angular/material/select"
import { AddCustomerDialogComponent } from "./salespoint/customers/add-customer-dialog/add-customer-dialog.component"
import { DefaultOptions } from "apollo-client"
import { ApplicationsComponent } from "./salespoint/applications/applications.component"
import { CreateApplicationDialogComponent } from "./salespoint/applications/create-application-dialog/create-application-dialog.component"
import { DeliveryComponent } from "./salespoint/delivery/delivery.component"
import { CreateDeliveryDialogComponent } from "./salespoint/delivery/create-delivery-dialog/create-delivery-dialog.component"
import { onError } from "apollo-link-error"
import {showErrorMessage} from "./admin/crud-tables/common/insert-dialog/insert-dialog.component"
import {InMemoryCache} from "apollo-cache-inmemory"
import { SaleComponent } from "./admin/crud-tables/sale.component"
import { SellerComponent } from "./admin/crud-tables/seller.component"
import { CustomerComponent } from "./admin/crud-tables/customer.component"
import { InsertDialogComponent } from "./admin/crud-tables/common/insert-dialog/insert-dialog.component"
import { AreaComponent } from "./admin/crud-tables/area.component"
import {GraphQLError} from "graphql"
import { AnalyticsQueryDialogComponent } from "./admin/analytics/analytics-query-dialog/analytics-query-dialog.component"
import {DeliveryComponent as PlainDeliveryComponent} from "./admin/crud-tables/delivery.component"
import { ApplicationComponent as PlainApplicationComponent} from "./admin/crud-tables/application.component"
import { ApplicationProductComponent } from "./admin/crud-tables/application-product.component"
import {OrderComponent} from "./admin/crud-tables/order.component"
import {OrderProductComponent} from "./admin/crud-tables/order-product.component"
import {SaleProductComponent} from "./admin/crud-tables/sale-product.component"
import { SupplierComponent } from "./admin/crud-tables/supplier.component"
import {ShopDeliveryComponent} from "./admin/crud-tables/shop_delivery.component"
import {ShopDeliveryProductComponent} from "./admin/crud-tables/shop-delivery-product.component"
import {SalesPointProductComponent} from "./admin/crud-tables/salespoint-product.component";
import {SupplierPriceComponent} from "./admin/crud-tables/supplier-price.component";
import {onNetworkError} from "./error-handler";


@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    LoginComponent,
    UserInfoComponent,
    TopbarComponent,

    ProductsComponent,

    SalesPointsComponent,

    AnalyticsComponent,

    WelcomeComponent,

    StorageComponent,

    CustomersComponent,

    SalesComponent,
    SalesInfoDialogComponent,
    CreateSaleDialogComponent,

    AddCustomerDialogComponent,
    ApplicationsComponent,

    CreateApplicationDialogComponent,
    DeliveryComponent,
    CreateDeliveryDialogComponent,
    SaleComponent,
    SellerComponent,
    CustomerComponent,
    InsertDialogComponent,
    AreaComponent,
    AnalyticsQueryDialogComponent,
    PlainDeliveryComponent,
    PlainApplicationComponent,
    ApplicationProductComponent,
    OrderComponent,
    OrderProductComponent,
    SaleProductComponent,
    SupplierComponent,
    ShopDeliveryComponent,
    ShopDeliveryProductComponent,
    SalesPointProductComponent,
    SupplierPriceComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        ApolloModule,
        HttpLinkModule,
        BrowserModule,
        AppRoutingModule,
        NoopAnimationsModule,
        MatMenuModule,
        MatIconModule,
        MatButtonModule,
        AgGridModule,
        ReactiveFormsModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        FormsModule,
        MatSelectModule
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(
    apollo: Apollo,
    httpLink: HttpLink
  ) {
    const http = httpLink.create({ uri: environment.backendUrl })

    const errorLink = onError(({graphQLErrors, networkError}) => {
      if (networkError){
        onNetworkError(networkError)
        return
      }
      if (graphQLErrors) {
        graphQLErrors.forEach((err) => console.log(JSON.stringify(err)))
        const readableErrors = graphQLErrors.filter(isErrorReadable)
        console.log("readable errors", readableErrors)
        if (readableErrors.length !== 0){
          readableErrors.forEach(showReadableError)
          return
        }
        graphQLErrors.map(graphQLError => {
          showErrorMessage(graphQLError)
        })
      }
    })
    apollo.create({
      link: errorLink.concat(http),
      defaultOptions: DEFAULT_APOLLO_OPTS,
      cache: new InMemoryCache()
    })
  }
}

const DEFAULT_APOLLO_OPTS: DefaultOptions = {
  watchQuery: {
    fetchPolicy: "no-cache",
    errorPolicy: "none",
  },
  mutate: {
    fetchPolicy: "no-cache",
    errorPolicy: "none",
  }
}

function showReadableError(err: GraphQLError) {
  console.log(err)
  alert(`ОШИБКА: ${err.message}`)
}

function isErrorReadable(err: GraphQLError): boolean {
  if (!err.message.match(".*[А-Я]|[а-я].*")){
    return false
  }
  return err.locations.length === 0;
}
