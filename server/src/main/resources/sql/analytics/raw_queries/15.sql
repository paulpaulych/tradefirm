--данные о товарообороте данной торговой точки
select
    sp.product_id,
    sum(sp.count) as total_count
from sale s
    join sale_product sp on s.id = sp.sale_id
where s.sales_point_id = ?