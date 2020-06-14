-- посавки определенного товара указанным поставщиком
select
    d.id,
    di.count
from delivery d
        join shop_delivery sd on d.id = sd.delivery_id
        join shop_delivery_items di on di.shop_delivery_id = sd.id
where di.product_id = ?
  and d.supplier_id = ?;