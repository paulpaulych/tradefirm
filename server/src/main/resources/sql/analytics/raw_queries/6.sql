--данные по выработке отдельно взятого продавца
select
    sum(sp.product_id * sp.count * st.price) as personal_production
from sale s
    inner join sale_product sp on s.sale_id = sp.sale_id
    inner join storage st on (s.sales_point_id = st.sales_point_id
         and sp.product_id = st.product_id)
where s.date > ?
  and s.date < ?
  and s.customer_id = ?
  and s.sales_point_id = ?
