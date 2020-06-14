-- Cведения о поставках товара по данному номеру заказа
select
    id
from delivery
where order_id = ?