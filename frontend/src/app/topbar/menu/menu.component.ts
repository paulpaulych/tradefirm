import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  items = [
    { name: "Продукты", link: "products" },
    { name: "Журнал покупок", link: "sales" },
    { name: "Точки продаж", link: "salesPoints" },
    { name: "Сотрудники", link: "employees" },
    { name: "Покупатели", link: "customers" },
    { name: "Аналитика", link: "analytics"}
  ]

  ngOnInit(): void {
  }
}
