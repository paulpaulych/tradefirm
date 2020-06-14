--данные о товарообороте данной торговой точки
select
    sp.product_id as id,
    sum(sp.count) as totally_sold
from sale s
    join sale_product sp on s.id = sp.sale_id
where s.sales_point_id = ?
group by sp.product_id