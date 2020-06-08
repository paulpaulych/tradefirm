import { async, ComponentFixture, TestBed } from "@angular/core/testing"

import { InsertDialogComponent } from "./insert-dialog.component"
import {Product} from "../../products/product"

describe("InsertDialogComponent", () => {
  let component: InsertDialogComponent<Product>
  let fixture: ComponentFixture<InsertDialogComponent<Product>>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsertDialogComponent ]
    })
    .compileComponents()
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(InsertDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it("should create", () => {
    expect(component).toBeTruthy()
  })
})
