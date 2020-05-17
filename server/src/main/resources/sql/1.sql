-- поставщики которые поставляли определенный продукт(или их количество)
select
     distinct(s.company_name)
--     count(distinct(s.company_name))
from supplier s
         join delivery d on s.supplier_id = d.supplier_id
         join delivery_distribution dd on dd.delivery_id = d.delivery_id
where product_id = ?;

-- поставщики поставлявшие некоторый товарв обЪеме не менее заданного
-- TODO: поставлявшие хотя бы раз или единожды?
select --хотя бы раз поставляли нужный объем
    supplier_id
from supplier s
where supplier_id in ( --объем
    select
           supplier_id
    from delivery d
    where ? <= (
        select sum(dd.count)
        from delivery_distribution dd
        where dd.product_id = ?
            and dd.delivery_id = d.delivery_id
    )
);
select --общий объем некоторого товара по всем поставкам больше заданного
    distinct(s.supplier_id)
--      distinct(s.company_name)
from supplier s
    where 80 <=(
        select
            sum(dd.count)
            from delivery d
                join delivery_distribution dd on d.delivery_id = dd.delivery_id
            where d.supplier_id = s.supplier_id
                and dd.product_id = 2
        );
select --общий объем некоторого товара по всем поставкам больше заданного
        --за указанный период
    distinct(s.supplier_id)
--      distinct(s.company_name)
from supplier s
where ? <= (
    select
        sum(dd.count)
    from delivery d
             join delivery_distribution dd on d.delivery_id = dd.delivery_id
    where d.supplier_id = s.supplier_id
      and dd.product_id = 2
      and d.date > ?
      and d.date < ?
);