-- Cведения о поставках товара по данному номеру заказа
select
    d.id
from delivery d
    where d.order_id = ?