-- поставщики которые поставляли определенный продукт
select distinct on( s.id)
    s.id,
    s.company_name
from supplier s
    join delivery d on s.id = d.supplier_id
    join shop_delivery sd on d.id = sd.delivery_id
    join shop_delivery_items i on i.shop_delivery_id = sd.id
where i.product_id = ?;