--зп продавцов

select
    e.salary
from sales_point sp
    join employee e on e.sales_point_id = sp.sales_point_id
    join customer c on c.customer_id = e.employee_id

--по типу ТТ
select
    e.salary
from sales_point sp
         join employee e on e.sales_point_id = sp.sales_point_id
         join customer c on c.customer_id = e.employee_id
where sp.type = ?;

--по конкретной ТТ
select
    e.salary
from sales_point sp
         join employee e on e.sales_point_id = sp.sales_point_id
         join customer c on c.customer_id = e.employee_id
where sp.sales_point_id = ?;


