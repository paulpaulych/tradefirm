import { Component, OnInit } from "@angular/core"
import {MatDialogRef} from "@angular/material/dialog"
import {CustomersRepoService} from "../customers-repo.service"

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
