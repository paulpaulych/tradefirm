import { TestBed } from "@angular/core/testing"

import { DeliveryRepoService } from "./delivery-repo.service"

describe("DeliveryRepoService", () => {
  let service: DeliveryRepoService

  beforeEach(() => {
    TestBed.configureTestingModule({})
    service = TestBed.inject(DeliveryRepoService)
  })

  it("should be created", () => {
    expect(service).toBeTruthy()
  })
})
