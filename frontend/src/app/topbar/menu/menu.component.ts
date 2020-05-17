import {Component, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  // @ViewChild(MatMenuTrigger) trigger: MatMenuTrigger;

  items = [
    { name: "Продукты", link: "products" },
    { name: "Журнал покупок", link: "sales" },
    { name: "Точки продаж", link: "salesPoints" },
    { name: "Сотрудники", link: "employees" },
    { name: "Покупатели", link: "customers" }
  ]

  ngOnInit(): void {
  }
}
