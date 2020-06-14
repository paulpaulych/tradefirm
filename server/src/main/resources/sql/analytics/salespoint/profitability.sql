--рентабельность торговых точек(отношение объема продаж к накладным расходам)
select
    sp.id as sales_point_id,
    sv.sales_voulme / (a.rent_price + a.municipal_services_price + tss.total_seller_salary) as profitability
from sales_point sp
         join area a on sp.area_id = a.id
         join (select--объем прдаж по всем ТТ
                     s.sales_point_id,
                     sum(sp.count * s2.price) as sales_voulme
               from sale s
                        join sale_product sp on s.id = sp.sale_id
                        join storage s2 on (sp.product_id = s2.product_id
                   and s.sales_point_id = s2.sales_point_id)
               group by s.sales_point_id
)sv on sp.id = sv.sales_point_id
         left
             join (
    select --тут суммарные зарплаты прожавцов по всем ТТ
           e.sales_point_id,
           sum(e.salary) as total_seller_salary
    from seller e
    group by e.sales_point_id
)tss on tss.sales_point_id = sp.id
where sp.type != 'Палатка'