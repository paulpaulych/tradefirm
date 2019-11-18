select
    p.product_id,
    p.product_name,
    s.count
from storage s
    join product p on s.product_id = p.product_id
where sales_point_id = 1;