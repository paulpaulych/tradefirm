--Покупатели, покупившие заданный товар в суммарном объеме не менее заданного
select distinct on(id)
    id,
    name,
    total_count as totallyBought
from (
         select
             c.id,
             c.name,
             sum(sp.count) over(partition by c.id) as total_count
         from customer c
                  join sale s on s.customer_id = c.id
                  join sale_product sp on s.id = sp.sale_id
         where product_id = ?
     )total_count
where total_count.total_count >= ?;