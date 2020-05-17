--объем продаж данного товара по всем торг точкам
select
    sum(sp.count) as total_count
from sale s
     join sale_product sp on s.sale_id = sp.sale_id
where sp.product_id = ?
  and s.date < ?
  and s.date > ?;

--по типу ТТ
select
    sum(ps.count) as total_count
from sale s
    join sale_product ps on s.sale_id = ps.sale_id
    join sales_point sp on s.sales_point_id = sp.sales_point_id
where sp.type = ?
    and ps.product_id = ?
    and s.date < ?
    and s.date > ?


-- по ктнкретной ТТ
select
    sum(ps.count) as total_count
from sale s
     join sale_product ps on s.sale_id = ps.sale_id
     join sales_point sp on s.sales_point_id = sp.sales_point_id
where sp.type = ?
  and ps.product_id = ?
  and s.date < ?
  and s.date > ?