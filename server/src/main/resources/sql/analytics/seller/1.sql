--данные по выработке на одного продавца
select
        sum(sp.product_id * sp.count * st.price) / (select count(*) from seller)
from sale s
         inner join sale_product sp on s.id = sp.sale_id
         inner join storage st on (s.sales_point_id = st.sales_point_id
                    and sp.product_id = st.product_id)