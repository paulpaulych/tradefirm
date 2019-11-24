-- 7) Выбрать товары поставляемые одним из перечисленных поставщиков (IN)

-- 1
explain analyse
select
    distinct  p.*
from supplier_price_list spl
         join product p on spl.product_id = p.product_id
where spl.supplier_id in (
    values (1), (2),(3), (4),(5), (6),(7), (8),(9), (10),(11), (12),(13), (14),(15), (16),(17), (18),(19), (20),(21), (22),(23), (24),(25), (26),(27), (28),(29), (30),(31), (32),(33), (34)
);

--2
explain analyse
select
    distinct on(p.product_id)
    p.product_id,
    p.product_name
from supplier_price_list spl
         join product p on spl.product_id = p.product_id
where spl.supplier_id in (
    values (1), (2),(3), (4),(5), (6),(7), (8),(9), (10),(11), (12),(13), (14),(15), (16),(17), (18),(19), (20),(21), (22),(23), (24),(25), (26),(27), (28),(29), (30),(31), (32),(33), (34)
);

--3
explain analyse
select
    *
from product p
where product_id in(
    select
          spl.product_id
    from supplier_price_list spl
    where spl.supplier_id in (
        values (1), (2),(3), (4),(5), (6),(7), (8),(9), (10),(11), (12),(13), (14),(15), (16),(17), (18),(19), (20),(21), (22),(23), (24),(25), (26),(27), (28),(29), (30),(31), (32),(33), (34)
    )
);

--4
explain analyse
select
    p.*
from product p
where p.product_id in(
    select
        distinct spl.product_id
    from supplier_price_list spl
    where spl.supplier_id in (
        values (1), (2),(3), (4),(5), (6),(7), (8),(9), (10),(11), (12),(13), (14),(15), (16),(17), (18),(19), (20),(21), (22),(23), (24),(25), (26),(27), (28),(29), (30),(31), (32),(33), (34)
    )
);

--вывод:
-- нет смысла делать where in (select distinct ...)
-- без distinct скорость та же

-- 1 место. 1
-- 2 место. 3, 4
-- рез-т меняется вместе с данным

-- 3 место. 2


