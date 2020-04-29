import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProductsComponent} from './products/products.component';

const routes: Routes = [];

@NgModule({
  imports: [
    RouterModule.forRoot([
      { path: 'products', component: ProductsComponent }
    ])
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
