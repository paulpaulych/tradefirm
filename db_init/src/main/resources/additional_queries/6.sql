--6) Выбрать поставщиков, которые поставляют продукты, в названии которых есть подсторка "Хлеб" (с учётом регистра)

select distinct spl.supplier_id
from supplier_price_list spl
    join product p on spl.product_id = p.product_id
where p.product_name ilike '%Хлеб%'
