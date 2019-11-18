
select
    d.*,
    dd.count
from delivery d
    inner join delivery_distribution dd on d.delivery_id = dd.delivery_id
where d.supplier_id = ?
    and dd.product_id = ?;

select
    d.*,
    dd.count
from delivery d
    inner join delivery_distribution dd on d.delivery_id = dd.delivery_id
where d.supplier_id = ?
  and dd.product_id = ?
  and d.date >= ?
  and d.date <= ?