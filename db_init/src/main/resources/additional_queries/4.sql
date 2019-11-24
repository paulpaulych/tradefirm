--4) Выбрать все склады, на которых не хранится ни одного товара

select
    sp.sales_point_id
from sales_point sp
where sales_point_id in (
    select
        tc.sales_point_id
    from (select
             s.sales_point_id,
             sum(s.count) as total_count
          from storage s
          group by s.sales_point_id
         ) tc
    where tc.total_count < 0
);
