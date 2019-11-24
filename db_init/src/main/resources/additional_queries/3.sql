-- Выбрать все продукты, в названии которых есть подстрока "фито" (без учёта регистра)
select
    p.product_id,
    p.product_name
from product p
-- where product_name ilike '%фито%'
where product_name ilike '%ло%'