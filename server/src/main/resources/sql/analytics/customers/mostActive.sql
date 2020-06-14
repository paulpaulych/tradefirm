-- Топ покупателей по количеству покупок
select
    s.customer_id,
    count(s.id) as sales_count
from sale s
group by s.customer_id
order by sales_count desc