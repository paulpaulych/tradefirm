-- отношение объема продаж к объему площадей
select
    sp.id as sales_point_id,
    (sv.sales_voulme / a.square) as ratio
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
where sp.type != 'Палатка';