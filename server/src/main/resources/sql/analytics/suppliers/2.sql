-- поставщики которые поставили заданный торав в суммарном объеме больше заданного
select
    s.id,
    s.company_name as companyName,
    t.total_supplied as totallySupplied
from(
        select
            sum(dd.count) as total_supplied,
            s.id as supplier_id
        from delivery d
                 join supplier s on d.supplier_id = s.id
                 join delivery_distribution dd on d.id = dd.delivery_id
        where d.supplier_id = s.id
          and dd.product_id = ?
--          and d.date > ?
--          and d.date < ?
        group by s.id
) t join supplier s on t.supplier_id = s.id
where total_supplied >= ?



