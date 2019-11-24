-- ведения о поставках товара по данному номеру заказа

select
    d.delivery_id
from delivery d
    where order_id = ?