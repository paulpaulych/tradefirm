import { Component, OnInit } from "@angular/core"
import {MatDialogRef} from "@angular/material/dialog"
import {StorageRepoService} from "../../storage/storage-repo.service"
import {ApplicationRepoService} from "../application-repo.service"

@Component({
  selector: "app-create-application-dialog",
  templateUrl: "./create-application-dialog.component.html"
})
export class CreateApplicationDialogComponent implements OnInit {

  items = [{}]

  storageItems
  filteredStorageItems

  constructor(
    private dialogRef: MatDialogRef<CreateApplicationDialogComponent>,
    private storageRepoService: StorageRepoService,
    private applicationRepoService: ApplicationRepoService) { }

  ngOnInit(): void {
    this.loadStorageItems()
  }

  loadStorageItems(){
    this.storageRepoService.queryData(data => {
      this.storageItems = data
      this.filteredStorageItems = this.storageItems
    })
  }

  closeDialog() {
    this.dialogRef.close()
  }

  createApplication(){
    this.applicationRepoService.createApplication(this.items)
      .subscribe(({ data }) => {
        this.closeDialog()
        alert(`Заявка успешно добавлена: id = ${data.createApplication.id}`)
      })
  }

  onStorageItemKey(value) {
    this.filteredStorageItems = this.filterStorageItemsByProductName(value.target.value)
  }

  filterStorageItemsByProductName(value: string) {
    const filter = value.toLowerCase()
    return this.storageItems.filter(storageItem =>
      storageItem.productName.toLowerCase().startsWith(filter)
    )
  }

  dropFilter(){
    this.filteredStorageItems = this.storageItems
  }

  addApplicationItem() {
    this.items.push({})
  }

  removeApplicationItem() {
    this.items.pop()
  }

}
