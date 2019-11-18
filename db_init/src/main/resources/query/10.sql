--отнощение объема продаж к объему торг площадей
select
    sp.sales_point_id,
    sv.sales_voulme / a.square as rentability
from sales_point sp
    join area a on sp.area_id = a.area_id
    join (select--объем прдаж по всем ТТ
                s.sales_point_id,
                sum(sp.count * s2.price) as sales_voulme
          from sale s
                   join sale_product sp on s.sale_id = sp.sale_id
                   join storage s2 on (sp.product_id = s2.product_id
              and s.sales_point_id = s2.sales_point_id)
          group by s.sales_point_id
    )sv on sp.sales_point_id = sv.sales_point_id
where sp.type = 'Универмаг';

--отнощение объема продаж к числу прилавков
select
    sp.sales_point_id,
    sv.sales_voulme / a.stall_count as rentability
from sales_point sp
         join area a on sp.area_id = a.area_id
         join (select--объем прдаж по всем ТТ
                     s.sales_point_id,
                     sum(sp.count * s2.price) as sales_voulme
               from sale s
                        join sale_product sp on s.sale_id = sp.sale_id
                        join storage s2 on (sp.product_id = s2.product_id
                   and s.sales_point_id = s2.sales_point_id)
               group by s.sales_point_id
)sv on sp.sales_point_id = sv.sales_point_id
where sp.type = 'Универмаг'