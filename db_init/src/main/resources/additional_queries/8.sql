--8) Вывести всех поставщиков и среднюю цену товара, у которых средняя цена товара превышает 100

--можно ли одним селектом обойтись? post-where или что-нибудь такое
select
    supplier_id,
    avg_price::numeric(64,2)
from (
         select supplier_id,
                avg(price) as avg_price
         from supplier_price_list spl
         group by supplier_id
     )avg
where avg.avg_price > 2000
