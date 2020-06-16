--каждый раз считаем сумму покупки

--данные по выработке на одного продавца
select
    sum(sp.product_id * sp.count * st.price)
        / (select count(*) from seller) as production
from sale s
    inner join sale_product sp on s.sale_id = sp.sale_id
    inner join storage st on (s.sales_point_id = st.sales_point_id
        and sp.product_id = st.product_id)
where s.date > ?
    and s.date < ?;

--данные по выработке на одного продавца по ТТ указанного типа
select
        sum(sp.product_id * sp.count * st.price)
        / (select count(*) from seller) as production
from sale s
         inner join sale_product sp on s.sale_id = sp.sale_id
         inner join storage st on (s.sales_point_id = st.sales_point_id
    and sp.product_id = st.product_id)
         inner join sales_point on s.sales_point_id = sales_point.sales_point_id
where s.date > ?
  and s.date < ?
  and sales_point.type = 'Палатка';
