--зарплаты продавцой данной торговой точки
select
    s.id,
    s.salary
from seller s
where s.sales_point_id = ?
