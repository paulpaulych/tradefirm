import { BrowserModule } from "@angular/platform-browser"
import { NgModule } from "@angular/core"

import { AppRoutingModule } from "./app-routing.module"
import { AppComponent } from "./app.component"
import { NavigationComponent } from "./topbar/navigation/navigation.component"
import { NoopAnimationsModule } from "@angular/platform-browser/animations"
import {MatMenuModule} from "@angular/material/menu"
import {MatIconModule} from "@angular/material/icon"
import {MatButtonModule} from "@angular/material/button"
import {ProductsComponent} from "./admin/products/products.component"
import {AgGridModule} from "ag-grid-angular"
import {Apollo, APOLLO_OPTIONS, ApolloModule} from "apollo-angular"

import {HttpLink, HttpLinkModule} from "apollo-angular-link-http"
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http"
import { LoginComponent } from "./security/login/login.component"
import {FormsModule, ReactiveFormsModule} from "@angular/forms"
import {AuthInterceptor} from "./security/auth-interceptor.service"
import { UserInfoComponent } from "./topbar/userinfo/user-info.component"
import { TopbarComponent } from "./topbar/topbar.component"
import {SalesPointsComponent} from "./admin/sales-point/sales-points.component"
import {ProductRepo} from "./admin/products/product-repo.service"
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
import {GraphQLError} from "graphql"
import {showErrorMessage} from "./admin/grid-common/insert-dialog/insert-dialog.component"
import {message} from "ag-grid-community/dist/lib/utils/general"
import {ApolloLink, concat} from "apollo-link"
import {Router, RouterModule} from "@angular/router";
import {InMemoryCache} from "apollo-cache-inmemory";
import { SaleComponent } from './admin/sale/sale.component';
import { SellerComponent } from './admin/seller/seller.component';
import { CustomerComponent } from './admin/customer/customer.component';
import { InsertDialogComponent } from './admin/grid-common/insert-dialog/insert-dialog.component';


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
    InsertDialogComponent
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
    ProductRepo,
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
    httpLink: HttpLink,
    router: Router
  ) {
    const http = httpLink.create({ uri: environment.backendUrl })


    const errorLink = onError(({ graphQLErrors, networkError }) => {
      if (graphQLErrors) {
        graphQLErrors.map(graphQLError => {
          const errorType = graphQLError["errorType"]
          console.log("error type: " + errorType)
          if (errorType === "READABLE_ERROR"){
            alert(graphQLError.message)
            return
          }
          showErrorMessage(graphQLError)
        })
      }
      if (networkError){
        onNetworkError(networkError)
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

export function onNetworkError(err) {
  alert("Сервер недоступен. Проверьте подключение.")
  console.log(`[NETWORK ERROR]: ${err}`)
}
