--покупатели хотя бы раз покупавшие заданный товар
select distinct on (c.id)
    c.id,
    c.name
from customer c
         join sale s on s.customer_id = c.id
         join sale_product sp on s.id = sp.sale_id
where sp.product_id = ?;
