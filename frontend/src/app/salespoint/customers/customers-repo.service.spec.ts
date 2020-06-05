import { TestBed } from "@angular/core/testing"

import { CustomersRepoService } from "./customers-repo.service"

describe("CustomersRepoService", () => {
  let service: CustomersRepoService

  beforeEach(() => {
    TestBed.configureTestingModule({})
    service = TestBed.inject(CustomersRepoService)
  })

  it("should be created", () => {
    expect(service).toBeTruthy()
  })
})
