import { async, ComponentFixture, TestBed } from "@angular/core/testing"

import { CreateDeliveryDialogComponent } from "./create-delivery-dialog.component"

describe("CreateDeliveryDialogComponent", () => {
  let component: CreateDeliveryDialogComponent
  let fixture: ComponentFixture<CreateDeliveryDialogComponent>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateDeliveryDialogComponent ]
    })
    .compileComponents()
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDeliveryDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it("should create", () => {
    expect(component).toBeTruthy()
  })
})
