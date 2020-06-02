import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {Apollo} from "apollo-angular";
import gql from "graphql-tag";
import {showErrorMessage} from "../../../admin/grid-common/insert-grid";
import {StorageRepoService} from "../../storage/storage-repo.service";
import {CustomersRepoService} from "../../customers/customers-repo.service";
import {AddCustomerDialogComponent} from "../../customers/add-customer-dialog/add-customer-dialog.component";
import {FormBuilder, Validators} from "@angular/forms";

class CartItem {
  productId
  count
}

const CREATE_SALE_MUTATION = gql`
mutation CreateSale($customerId: Long, $cart: [CartItemInput!]!){
  createSale(customerId: $customerId, cart: $cart){
    id
    seller{
      id
      name
    }
    customer{
      id
      name
    }
    salesPoint{
      id
      type
    }
    cartItems{
      product{
        id
        name
      }
      count
    }
  }

}
`

@Component({
  selector: 'app-create-sale-dialog',
  templateUrl: './create-sale-dialog.component.html',
  styleUrls: ['./create-sale-dialog.component.css']
})
export class CreateSaleDialogComponent implements OnInit {

  customerId
  cart = [{}]

  storageItems
  filteredStorageItems

  customers
  filteredCustomers
  form = this.formBuilder.group({})

  constructor(
    private dialogRef: MatDialogRef<CreateSaleDialogComponent>,
    private apollo: Apollo,
    private storageRepoService: StorageRepoService,
    private customersRepoService: CustomersRepoService,
    private dialog: MatDialog,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.loadStorageItems()
    this.loadCustomers()
  }

  loadStorageItems(){
    this.storageRepoService.queryData(data=>{
      this.storageItems = data
      this.filteredStorageItems = this.storageItems
    })
  }

  loadCustomers(){
    this.customersRepoService.queryData(data=>{
      this.customers = data
      this.filteredCustomers = data
    })
  }

  closeDialog() {
    this.dialogRef.close();
  }

  createSale(){
    const isValid = this.isCartValid((s)=>alert(s))
    if(!isValid){
      return
    }

    this.apollo.mutate({
      mutation: CREATE_SALE_MUTATION,
      variables: {
        customerId: this.customerId,
        cart: this.cart.filter((ci)=>ci["productId"] && ci["count"])
      }
    })
      .subscribe(({ data }) => {
        this.closeDialog()
        alert(`Покупка успешно добавлена: ${JSON.stringify(data["createSale"])}`)
      },(error) => {
        showErrorMessage(error)
      })
  }

  onStorageItemKey(value) {
    this.filteredStorageItems = this.filterStorageItemsByProductName(value.target.value);
  }

  onCustomerKey(value){
    this.filteredCustomers = this.filterCustomersByName(value.target.value);
  }

  filterCustomersByName(value: string){
    let filter = value.toLowerCase();
    return this.customers.filter(customer =>
      customer.name.toLowerCase().startsWith(filter)
    )
  }

  filterStorageItemsByProductName(value: string) {
    let filter = value.toLowerCase();
    return this.storageItems.filter(storageItem =>
      storageItem.productName.toLowerCase().startsWith(filter)
    );
  }

  dropFilters(){
    this.filteredStorageItems = this.storageItems
    this.filteredCustomers = this.customers
  }

  addCartItem() {
    this.cart.push({})
 }

  removeCartItem() {
    this.cart.pop()
  }

  openAddCustomerDialog() {
    const dialogRef = this.dialog.open(AddCustomerDialogComponent, {
      width: '60%'
    });
    dialogRef.afterClosed().subscribe(()=>{
        this.loadCustomers()
        this.dropFilters()
    })
  }


  isCartValid(onError: (string)=>void){
    const emptyItems = this.cart.filter((it)=>{
      return !it["productId"] || !it["count"]
    })
    if(emptyItems.length != 0){
      onError("Сперва заполните все поля")
      return false
    }

    const productsSet = new Set(this.cart.map((it)=> {
      return it["productId"];
    }))
    if(productsSet.size != this.cart.length){
      onError("Продукты не должны повторяться")
      return false
    }
    return true
  }

  private checkIfInteger(value){
    return ((parseFloat(value) == parseInt(value)) && !isNaN(value));
  }
}
