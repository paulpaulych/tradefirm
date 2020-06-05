--Supplier
drop table if exists public.supplier cascade;
CREATE TABLE public.supplier(
 id  bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 company_name varchar(256) NOT NULL
);
-- public.customer
drop table if exists public.customer cascade;
CREATE TABLE public.customer(
 id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 name varchar(50) NOT NULL
);
-- public.area
drop table if exists public.area cascade;
CREATE TABLE public.area(
 id                 bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 square                  bigint NOT NULL,
 rent_price              numeric(64, 2) NOT NULL,
 municipal_services_price numeric(64, 2) NOT NULL,
 stall_count             integer NOT NULL
);
--  public.order
drop table if exists public.orders cascade;
CREATE TABLE public.orders(
 id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 date       timestamp NOT NULL
);
-- public.product
drop table if exists public.product cascade;
CREATE TABLE public.product(
 id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 name varchar(50) NOT NULL
);
--  public.sales_point
drop table if exists public.sales_point cascade;
CREATE TABLE public.sales_point(
 id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 type         varchar(50) NOT NULL,
 area_id       bigint NULL REFERENCES public.area(id) on delete set null
);
CREATE INDEX sales_point_area_id_fkey ON public.sales_point(area_id);

--  public.employee
drop table if exists public.employee cascade;
CREATE TABLE public.employee(
 id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 name         varchar(256) NOT NULL,
 sales_point_id bigint NOT NULL,
 salary numeric(64, 2) NOT NULL
);
CREATE INDEX fkIdx_employee__sales_point_id ON employee(id);

-- Seller
drop table if exists public.seller cascade;
CREATE TABLE seller(
 id bigint primary key
     REFERENCES employee (id)
        on delete cascade
);

-- public.sale
drop table if exists public.sale cascade;
CREATE TABLE public.sale(
 id       bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 customer_id   bigint, -- покупатель может быть не указан
 sales_point_id bigint REFERENCES public.sales_point(id) on delete set null,
 employee_id   bigint REFERENCES seller(id) on delete set null,
 date         timestamp NOT NULL
);
CREATE INDEX fkIdx_sale__customer_id ON public.sale(customer_id);
CREATE INDEX fkIdx_sale__sales_point_id ON public.sale(sales_point_id);
CREATE INDEX fkIdx_sale__employee_id ON public.sale(employee_id);

--  public.sale_product
drop table if exists public.sale_product cascade;
CREATE TABLE public.sale_product(
 id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 sale_id    bigint  REFERENCES public.sale(id) on delete cascade,
 product_id bigint REFERENCES public.product(id) on delete cascade,
 count     integer NOT NULL
);
CREATE UNIQUE INDEX PK_SaleProduct ON public.sale_product(
 sale_id,
 product_id
);
CREATE INDEX fkIdx_340 ON public.sale_product(sale_id);
CREATE INDEX fkIdx_353 ON public.sale_product(product_id);

--  databases.application
drop table if exists public.application cascade;

CREATE TABLE public.application(
 id              bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 sales_point_id  bigint REFERENCES public.sales_point(id) on delete set null ,
 date          timestamp NOT NULL,
 is_new boolean default true
);
CREATE INDEX fkIdx_357 ON application( sales_point_id);

-- application_product
drop table if exists public.application_product cascade;
CREATE TABLE application_product(
 id             bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 application_id bigint REFERENCES public.application ( id ) on delete cascade,
 product_id     bigint REFERENCES public.product ( id ) on delete set null,
 count         integer NOT NULL
);
CREATE UNIQUE INDEX pk_product_application ON application_product(
 application_id,
 product_id
);
CREATE INDEX fkIdx_application_product__application_id ON application_product(application_id);
CREATE INDEX fkIdx_application_product__product_id ON application_product(product_id);

-- public.delivery - поставка от заказчика
drop table if exists public.delivery cascade;
CREATE TABLE public.delivery(
 id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 order_id bigint references public.orders(id),
 supplier_id bigint,
 date       timestamp NOT NULL
);
--  public.shop_delivery
drop table if exists public.shop_delivery cascade;
CREATE TABLE public.shop_delivery(
 id            bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 delivery_id   bigint NOT NULL REFERENCES public.delivery(id) on delete cascade,
 sales_point_id bigint NOT NULL REFERENCES public.sales_point(id) on delete cascade,
 date       timestamp NOT NULL
);
CREATE UNIQUE INDEX pk_shop_delivery ON public.shop_delivery (
 delivery_id,
 sales_point_id
);
CREATE INDEX fkIdx_shop_delivery__delivery_id ON public.shop_delivery( delivery_id);
CREATE INDEX fkIdx_shop_delivery__sales_point_id ON public.shop_delivery ( sales_point_id);

--  public.shop_delivery_items
drop table if exists public.shop_delivery_items cascade;
CREATE TABLE public.shop_delivery_items(
 id        bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 shop_delivery_id   bigint REFERENCES public.shop_delivery(id) on delete cascade,
 product_id bigint REFERENCES public.product(id) on delete cascade,
 count     integer NOT NULL
);
CREATE UNIQUE INDEX pkInd_shop_delivery__product ON public.shop_delivery_items(
 shop_delivery_id,
 product_id
);
CREATE INDEX fkIdx_product__shop_delivery_id ON public.shop_delivery_items( shop_delivery_id);
CREATE INDEX fkIdx_shop_delivery__product_id ON public.shop_delivery_items( product_id);

--  public.OrderProduct
drop table if exists public.order_product cascade;
CREATE TABLE public.order_product(
 id        bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 order_id   bigint REFERENCES public.orders(id) on delete cascade,
 product_id bigint REFERENCES public.product(id) on delete cascade,
 sales_point_id bigint REFERENCES public.sales_point(id) on delete cascade,
 count     integer NOT NULL
);
CREATE UNIQUE INDEX pkInd_order_product ON public.order_product(
 order_id,
 product_id,
 sales_point_id
);
CREATE INDEX fkIdx_order_product__order_id ON public.order_product( order_id);
CREATE INDEX fkIdx_order_product__product_id ON public.order_product( product_id);
CREATE INDEX fkIdx_order_product__sales_point_id ON public.order_product( sales_point_id);

--
-- -- public.section
-- drop table if exists public.section cascade;
-- CREATE TABLE public.section(
--  id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
--  floor     integer NOT NULL
-- );
--
-- -- section_manager
-- drop table if exists public.section_manager cascade;
-- CREATE TABLE section_manager(
--  id bigint  REFERENCES employee ( id ) on delete cascade,
--  section_id  bigint  REFERENCES section ( id ) on delete cascade
-- );
-- CREATE UNIQUE INDEX fkIdx_section_manager__emloyee_id ON section_manager(id);
-- CREATE INDEX fkIdx_section_manager__section_id ON section_manager(section_id);

--  public.storage
drop table if exists public.storage cascade;
CREATE TABLE public.storage(
 id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 sales_point_id bigint REFERENCES public.sales_point(id) on delete cascade on update cascade,
 product_id    bigint REFERENCES public.product(id) on delete cascade on update cascade,
 count        integer,
 price         numeric(64, 2)
);
CREATE UNIQUE INDEX pk_storage ON public.storage(
 sales_point_id,
 product_id
);
CREATE INDEX fkIdx_storage__sales_point_id ON public.storage(sales_point_id);
CREATE INDEX fkIdx_storage__product_id ON public.storage(product_id);

-- public.supplier_price_list
drop table if exists public.supplier_price_list cascade;
CREATE TABLE public.supplier_price_list(
 id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 supplier_id bigint REFERENCES public.supplier(id) on delete cascade,
 product_id  bigint REFERENCES public.product(id) on delete  cascade  on update cascade,
 price      numeric(64, 2) NOT NULL
);
CREATE UNIQUE INDEX pk_supplier_price_list ON public.supplier_price_list(
 supplier_id,
 product_id
);
CREATE INDEX fkIdx_supplier_price_list__supplier_id ON public.supplier_price_list(supplier_id);
CREATE INDEX fkIdx_supplier_price_list__product_id ON public.supplier_price_list(product_id);

--========================================================================================================
