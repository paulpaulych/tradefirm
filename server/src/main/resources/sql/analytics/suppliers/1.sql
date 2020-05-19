-- поставщики которые поставляли определенный продукт
select distinct on( s.id)
    s.id,
    s.company_name
--     count(distinct(s.company_name))
from supplier s
    join delivery d on s.id = d.supplier_id
    join delivery_distribution dd on dd.delivery_id = d.id
where dd.product_id = ?;