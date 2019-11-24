
--увеличить зп всех продавцов заданной торговой точки на 10 процентов
update employee
set salary = salary * 1.1
where sales_point_id = 4
and employee_id in (
    select
        seller.employee_id
    from seller
    );
--
-- select
--     *
-- from employee e
-- where e.sales_point_id = 4;
--
-- select
--     *
-- from employee e
-- where e.sales_point_id = 3;
