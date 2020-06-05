import { TestBed } from "@angular/core/testing"

import { ApplicationRepoService } from "./application-repo.service"

describe("ApplicationRepoService", () => {
  let service: ApplicationRepoService

  beforeEach(() => {
    TestBed.configureTestingModule({})
    service = TestBed.inject(ApplicationRepoService)
  })

  it("should be created", () => {
    expect(service).toBeTruthy()
  })
})
