-- поставщики которые поставили заданный торав в суммарном объеме больше заданного
select
    s.id,
    s.company_name,
    t.total_supplied as totally_supplied
from(
        select
            sum(i.count) as total_supplied,
            s.id as supplier_id
        from supplier s
            join delivery d on s.id = d.supplier_id
            join shop_delivery sd on d.id = sd.delivery_id
            join shop_delivery_items i on i.shop_delivery_id = sd.id
        where d.supplier_id = s.id
          and i.product_id = ?
        group by s.id
) t join supplier s on t.supplier_id = s.id
where total_supplied >= ?



