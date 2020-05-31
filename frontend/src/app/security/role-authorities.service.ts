import { Injectable } from '@angular/core';
import {ADMIN_PANEL_PATH, SALES_POINT_PATH} from "../app-routing.module";
import {AuthService} from "./auth-service.service";

@Injectable({
  providedIn: 'root'
})
export class RoleAuthoritiesService {

  private navigationBarContent = {
    ROLE_ADMIN: [
      { name: "Продукты", link: "/" + ADMIN_PANEL_PATH + "/products" },
      { name: "Журнал покупок", link: "/" + ADMIN_PANEL_PATH + "/sales" },
      { name: "Точки продаж", link: "/" + ADMIN_PANEL_PATH + "/salesPoints" },
      { name: "Сотрудники", link: "/" + ADMIN_PANEL_PATH + "/employees" },
      { name: "Покупатели", link: "/" + ADMIN_PANEL_PATH + "/customers" },
      { name: "Аналитика", link: "/" + ADMIN_PANEL_PATH + "/analytics"}
    ],
    ROLE_USER: [
      { name: "Склад", link: "/" + SALES_POINT_PATH + "/storage" },
      { name: "Покупки", link: "/" + SALES_POINT_PATH + "/sales" },
      { name: "Заявки", link: "/" + SALES_POINT_PATH + "/applications" },
      { name: "Поставки", link: "/" + SALES_POINT_PATH + "/deliveries" },
      { name: "Покупатели", link: "/" + SALES_POINT_PATH + "/customers" }
    ],
    default: []
  }

  private welcomePagePath = {
    ROLE_ADMIN: "/welcome",
    ROLE_USER: "/welcome",
    default: "/welcome"
  }

  constructor(private authService: AuthService) {}

  getNavigationBarContent(role){
    const content = this.navigationBarContent[role]
    if(content){
      return content
    }
    return this.navigationBarContent.default
  }

  welcomePage(role){
    console.log(role)
    const content = this.welcomePagePath[role]
    if(content){
      return content
    }
    return this.welcomePagePath.default
  }

}


