--сведения о покупателях данного товара
select
    distinct s.customer_id
from sale s
    join sale_product sp on s.id = sp.sale_id
where sp.product_id = ?

