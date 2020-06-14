-- Кол-во единиц проданного товара по контретной торговой точке
select
    sum(ps.count) as total_count
from sale s
         join sale_product ps on s.id = ps.sale_id
         join sales_point sp on s.sales_point_id = sp.id
where ps.product_id = ?
  and sp.id = ?