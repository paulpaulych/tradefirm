import { TestBed } from "@angular/core/testing"

import { CommonRepoService } from "./common-repo.service"

describe("CommonRepoService", () => {
  let service: CommonRepoService

  beforeEach(() => {
    TestBed.configureTestingModule({})
    service = TestBed.inject(CommonRepoService)
  })

  it("should be created", () => {
    expect(service).toBeTruthy()
  })
})
