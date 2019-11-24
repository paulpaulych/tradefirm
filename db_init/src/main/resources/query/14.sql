-- сведения о самых активных покупателях
select
    s.customer_id,
    count(s.sale_id) as sale_count
from sale s
group by s.customer_id
order by sale_count desc