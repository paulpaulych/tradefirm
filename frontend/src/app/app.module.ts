import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './topbar/menu/menu.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {ProductsComponent} from './admin/products/products.component';
import {AgGridModule} from 'ag-grid-angular';
import {Apollo, APOLLO_OPTIONS, ApolloModule} from 'apollo-angular';

import {HttpLink, HttpLinkModule} from 'apollo-angular-link-http';
import { InMemoryCache } from 'apollo-cache-inmemory';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './security/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {AuthInterceptor} from './security/auth-interceptor.service';
import { UserInfoComponent } from './topbar/userinfo/user-info.component';
import { TopbarComponent } from './topbar/topbar.component';
import {SalesPointsComponent} from './admin/sales-point/sales-points.component';
import {ProductRepo} from './admin/products/product-repo.service';
import { AnalyticsComponent } from './analytics/analytics.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    LoginComponent,
    UserInfoComponent,
    TopbarComponent,

    ProductsComponent,
    SalesPointsComponent,

    AnalyticsComponent
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
    ReactiveFormsModule
  ],
  providers: [
    ProductRepo,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: APOLLO_OPTIONS,
      useFactory: (httpLink: HttpLink) => {
        return {
          cache: new InMemoryCache(),
          link: httpLink.create({
            uri: "http://localhost:3000/graphql"
          })
        }
      },
      deps: [HttpLink]
    },

  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
