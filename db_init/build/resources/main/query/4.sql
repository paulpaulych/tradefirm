--объем и цены на указанный товар по всем торговым точкам
select
    p.product_id,
    s.count,
    s.price
from storage s
         join product p on s.product_id = p.product_id
where p.product_id = 1;

-- по всем торговым точкам указанного типа
select
    s.product_id,
    s.count,
    s.price,
    sp.type
from storage s
         join sales_point sp on s.sales_point_id = sp.sales_point_id
where s.product_id = 1
    and sp.type = 'Универмаг';

--объем и цены на указанный товар по указанной торговой точке
select
    s.count,
    s.price
from storage s
where s.product_id = 1
    and s.sales_point_id = ?