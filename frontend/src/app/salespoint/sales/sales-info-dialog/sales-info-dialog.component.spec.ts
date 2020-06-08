import { async, ComponentFixture, TestBed } from "@angular/core/testing"

import { SalesInfoDialogComponent } from "./sales-info-dialog.component"

describe("SalesInfoDialogComponent", () => {
  let component: SalesInfoDialogComponent
  let fixture: ComponentFixture<SalesInfoDialogComponent>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SalesInfoDialogComponent ]
    })
    .compileComponents()
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInfoDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it("should create", () => {
    expect(component).toBeTruthy()
  })
})
