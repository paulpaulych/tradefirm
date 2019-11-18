-- похоже на п.1. по аналогии
-- тут будут некореллированные подзапросы

--покупатели покупавшие данный товар
select
--     distinct(c.customer_id)
    *
from customer c
    join sale s on s.customer_id = c.customer_id
    join sale_product sp on s.sale_id = sp.sale_id
where product_id = 6;

--покупавшие товар в объеме не менее заданного(некореллированный)
select
    customer_id
from (
         select
                c.customer_id,
                sum(sp.count) over(partition by c.customer_id) as total_count
         from customer c
                  join sale s on s.customer_id = c.customer_id
                  join sale_product sp on s.sale_id = sp.sale_id
         where product_id = ?
     )total_count
    where total_count.total_count >= ?;

--покупавшие товар в объеме не менее заданного(некореллированный)
select
    customer_id
from (
         select
             c.customer_id,
             sum(sp.count) over(partition by c.customer_id) as total_count
         from customer c
                  join sale s on s.customer_id = c.customer_id
                  join sale_product sp on s.sale_id = sp.sale_id
         where product_id = 6
            and s.date > ?--start date
            and s.date < ?--end date
     )tc
where tc.total_count >= ?;--total_count
--
-- select
--     *
-- from customer c
--          join sale s on s.customer_id = c.customer_id
--          join sale_product sp on s.sale_id = sp.sale_id
-- where product_id = 9;