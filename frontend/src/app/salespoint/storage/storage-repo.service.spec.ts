import { TestBed } from "@angular/core/testing"

import { StorageRepoService } from "./storage-repo.service"

describe("StorageRepoService", () => {
  let service: StorageRepoService

  beforeEach(() => {
    TestBed.configureTestingModule({})
    service = TestBed.inject(StorageRepoService)
  })

  it("should be created", () => {
    expect(service).toBeTruthy()
  })
})
