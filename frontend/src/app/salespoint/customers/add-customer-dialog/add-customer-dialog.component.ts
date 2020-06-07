import { Component, OnInit } from "@angular/core"
import {MatDialogRef} from "@angular/material/dialog"
import {Apollo} from "apollo-angular"
import {StorageRepoService} from "../../storage/storage-repo.service"
import {CustomersRepoService} from "../customers-repo.service"
import {showErrorMessage} from "../../../admin/grid-common/insert-dialog/insert-dialog.component"

@Component({
  selector: "app-add-customer-dialog",
  templateUrl: "./add-customer-dialog.component.html",
  styleUrls: ["./add-customer-dialog.component.css"]
})
export class AddCustomerDialogComponent implements OnInit {
  customerName: string

  constructor(
    private dialogRef: MatDialogRef<AddCustomerDialogComponent>,
    private customersRepoService: CustomersRepoService) { }

  ngOnInit(): void {}

  closeDialog() {
    this.dialogRef.close()
  }

  addCustomer() {
    this.customersRepoService.addCustomer(this.customerName)
      .subscribe(({ data }) => {
        this.closeDialog()
        alert(`Покупатель успешно добавлен: ${JSON.stringify(data.createCustomer)}`)
      })
  }
}
