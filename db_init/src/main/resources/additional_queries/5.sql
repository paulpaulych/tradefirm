--5) Выбрать все склады, на которых хранится наиболее широкий ассортимент товаров

select
    tc.sales_point_id
from (
      select
          s.sales_point_id,
          row_number() over (order by count(s.product_id) desc ) as rank
      from storage s
      group by s.sales_point_id
    ) tc
where rank = 1;


select
    tc.sales_point_id,
    max(product_id_count)
from (
         select
             s.sales_point_id,
             count(s.product_id) as product_id_count
         from storage s
         group by s.sales_point_id
     ) tc
group by tc.sales_point_id;
