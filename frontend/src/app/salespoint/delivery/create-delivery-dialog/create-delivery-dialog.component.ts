import { Component, OnInit } from "@angular/core"
import {MatDialogRef} from "@angular/material/dialog"
import gql from "graphql-tag"
import {Apollo} from "apollo-angular"
import {DeliveryRepoService} from "../delivery-repo.service"

const PRODUCTS_QUERY = gql`
  query{
    products{
      id
      name
    }
  }
`

@Component({
  selector: "app-create-delivery-dialog",
  templateUrl: "./create-delivery-dialog.component.html",
  styleUrls: ["./create-delivery-dialog.component.css"]
})
export class CreateDeliveryDialogComponent implements OnInit {

  deliveryId

  items = [{}]

  products
  filteredProducts

  constructor(
    private dialogRef: MatDialogRef<CreateDeliveryDialogComponent>,
    private apollo: Apollo,
    private deliveryRepo: DeliveryRepoService) { }

  ngOnInit(): void {
    this.loadProducts()
  }

  loadProducts() {
    this.apollo.watchQuery<any>({
      query: PRODUCTS_QUERY
    })
      .valueChanges
      .subscribe( ({data, loading, errors}) => {
          console.log(`got: ${JSON.stringify(data)}`)
          this.products = data.products
          this.filteredProducts = this.products
      })
  }
  closeDialog() {
    this.dialogRef.close()
  }

  createApplication(){
    this.deliveryRepo.createDelivery(this.deliveryId, this.items)
      .subscribe(({ data }) => {
        this.closeDialog()
        alert(`Заявка успешно добавлена: ${JSON.stringify(data.createShopDelivery)}`)
      })
  }

  onStorageItemKey(value) {
    this.filteredProducts = this.filterStorageItemsByProductName(value.target.value)
  }

  filterStorageItemsByProductName(value: string) {
    const filter = value.toLowerCase()
    return this.products.filter(storageItem =>
      storageItem.productName.toLowerCase().startsWith(filter)
    )
  }

  dropFilter(){
    this.filteredProducts = this.products
  }

  addApplicationItem() {
    this.items.push({})
  }

  removeApplicationItem() {
    this.items.pop()
  }

}
