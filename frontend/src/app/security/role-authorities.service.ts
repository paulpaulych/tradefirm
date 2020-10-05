import { Injectable } from "@angular/core"


export const ADMIN_PANEL_PATH = "admin"
export const SALES_POINT_PATH = "salesPoint"

@Injectable({
  providedIn: "root"
})
export class RoleAuthoritiesService {

  private navigationBarContent = {
    ROLE_ADMIN: [
      { name: "Аналитика", link: "/" + ADMIN_PANEL_PATH + "/analytics"},
      { name: "Продукты", link: "/" + ADMIN_PANEL_PATH + "/products" },
      { name: "Покупки", link: "/" + ADMIN_PANEL_PATH + "/sales" },
      { name: "Покупка-Товар", link: "/" + ADMIN_PANEL_PATH + "/sale_product" },
      { name: "Точки продаж", link: "/" + ADMIN_PANEL_PATH + "/salesPoints" },
      { name: "Продавцы", link: "/" + ADMIN_PANEL_PATH + "/sellers" },
      { name: "Покупатели", link: "/" + ADMIN_PANEL_PATH + "/customers" },
      { name: "Помещения", link: "/" + ADMIN_PANEL_PATH + "/areas"},
      { name: "Поставки", link: "/" + ADMIN_PANEL_PATH + "/delivery"},
      { name: "Заявки", link: "/" + ADMIN_PANEL_PATH + "/application"},
      { name: "Заявкa-Продукт", link: "/" + ADMIN_PANEL_PATH + "/application_product"},
      { name: "Заказы", link: "/" + ADMIN_PANEL_PATH + "/order"},
      { name: "Заказ-Продукт", link: "/" + ADMIN_PANEL_PATH + "/order_product"},
      { name: "Поставщики", link: "/" + ADMIN_PANEL_PATH + "/supplier"},
      { name: "Поставка в магазин", link: "/" + ADMIN_PANEL_PATH + "/shop_delivery"},
      { name: "Поставка в магазин - Продукт", link: "/" + ADMIN_PANEL_PATH + "/shop_delivery_product"},
      { name: "Склады торговых точек", link: "/" + ADMIN_PANEL_PATH + "/storage"},
      { name: "Прайс поставщиков", link: "/" + ADMIN_PANEL_PATH + "/supplier_price"}
    ],
    ROLE_SELLER: [
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
    ROLE_SELLER: "/welcome",
    default: "/welcome"
  }

  getNavigationBarContent(role){
    const content = this.navigationBarContent[role]
    if (content){
      return content
    }
    return this.navigationBarContent.default
  }

  welcomePage(role){
    console.log(role)
    const content = this.welcomePagePath[role]
    if (content){
      return content
    }
    return this.welcomePagePath.default
  }

}


