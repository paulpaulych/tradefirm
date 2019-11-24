-- 9) Для всех покупателей вывести поставщиков, чьи товары они чаще всего покупают.
-- Если покупатель покупает у нескольких поставщиков одинаково часто, вывести поставщика с самым длинным названием

-- связать покупателя и поставщика невозможно, т.к один и тот же товар в одну и ту же торговую точку могут поставлять разные поставщики.

-- ДОПУЩЕНИЯ:
-- 1. ПОКУПАТЕЛЬ покупает ТОВАР у ПОСТАВЩИКА - значит что ПОКУПАТЕЛЬ покупает ТОВАР в торговой точке, в которую ПОСТАВЩИК хотя бы раз поставлял ТОВАР
-- 2. если названия одной длины, то выведет несколько

--решение:
--1. кричать
--2. получить ПОСТАВЩИКОВ, хотя бы раз поставлявшие ТОВАР в ТОРГОВУЮ ТОЧКУ
--3. количество покупок ПОКУПАТЕЛЕМ ТОВАРА в ТОРГОВОЙ ТОЧКЕ
--     a. кричать
--4. в рамках одного ПОКУПАТЕЛЯ:
--   ранжировать ПОСТАВЩИКОВ по колву покупок и длине названия
--   выбрать поставщика с наибольшим рангом

with ranked_suppliers_cte as ( -- cte только для удобства навигации по коду
    select customer_id,
           s_count.supplier_id,
           sales_count,
           sup.company_name,
           rank() over (PARTITION BY s_count.customer_id ORDER BY s_count.sales_count desc, char_length(sup.company_name) desc) as rank
--             rank() over (PARTITION BY s_count.customer_id ORDER BY s_count.sales_count desc) as rank
    from (
             select -- ПОКУПАТЕЛЬ покупал товар ПОСТАВЩИКА sales_count раз
                    c.customer_id,
                    supp.supplier_id,
                    count(s.sale_id) as sales_count
             from customer c
                      join sale s on c.customer_id = s.customer_id
                      join sale_product sprod on s.sale_id = sprod.sale_id
                      join ( -- left join? - нет
                 select --ПОСТАВЩИКИ, хотя бы раз поставлявшие ТОВАР в ТОРГОВУЮ ТОЧКУ
                        d.supplier_id,
                        dd.product_id,
                        dd.sales_point_id
                 from delivery d
                          join delivery_distribution dd on d.delivery_id = dd.delivery_id
             ) supp on (s.sales_point_id = supp.sales_point_id and supp.product_id = sprod.product_id)
             group by c.customer_id, supp.supplier_id
         ) s_count
        join supplier sup on sup.supplier_id = s_count.supplier_id --лишнее обращение к таблице.
                                                                   -- Не дешевле ли пробросить имя компании через больлшой джоин?
)
select
    customer_id,
    supplier_id,
    sales_count,
    company_name
from ranked_suppliers_cte
where rank = 1;
