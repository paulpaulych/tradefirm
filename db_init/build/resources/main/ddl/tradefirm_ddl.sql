--Supplier
drop table public.supplier cascade;
CREATE TABLE public.supplier(
 supplier_id  bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 company_name varchar(256) NOT NULL
);
-- public.customer
drop table public.customer cascade;
CREATE TABLE public.customer(
 customer_id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 customer_name varchar(50) NOT NULL
);
-- public.area
drop table public.area cascade;
CREATE TABLE public.area(
 area_id                 bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 square                  bigint NOT NULL,
 rent_price              integer NOT NULL,
 municipal_services_price integer NOT NULL,
 stall_count             integer NOT NULL
);
--  public.order
drop table public.order cascade;
CREATE TABLE public.order(
 order_id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 date       timestamp NOT NULL
);
-- public.product
drop table public.product cascade;
CREATE TABLE public.product(
 product_id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 product_name varchar(50) NOT NULL
);
--  public.sales_point
drop table public.sales_point cascade;
CREATE TABLE public.sales_point(
 sales_point_id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 type         varchar(50) NOT NULL,
 area_id       bigint NULL,
 CONSTRAINT fk_sales_point__area_id FOREIGN KEY ( area_id ) REFERENCES public.area(area_id)
);
CREATE INDEX fkIdx_sales_point__area_id ON public.sales_point( area_id);

--  public.employee
drop table public.employee cascade;
CREATE TABLE public.employee(
 employee_id   bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 name         varchar(256) NOT NULL,
 sales_point_id bigint NOT NULL,
 salary money NOT NULL
    --  ,CONSTRAINT fk_employee__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id)
);
CREATE INDEX fkIdx_employee__sales_point_id ON employee( sales_point_id);

-- Seller
drop table public.seller cascade;
CREATE TABLE seller(
 employee_id bigint primary key,
 CONSTRAINT fk_seller__employee_id FOREIGN KEY ( employee_id ) REFERENCES employee ( employee_id )
);

-- public.sale
drop table public.sale cascade;
CREATE TABLE public.sale(
 sale_id       bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 customer_id   integer, -- покупатель может быть не указан
 sales_point_id bigint,
 employee_id   bigint,
 date         timestamp NOT NULL,
 --CONSTRAINT fk_sale__customer_id FOREIGN KEY ( customer_id ) REFERENCES public.customer(customer_id),
 CONSTRAINT fk_sale__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id),
 CONSTRAINT fk_sale__employee_id FOREIGN KEY ( employee_id ) REFERENCES seller(employee_id)
);
CREATE INDEX fkIdx_sale__customer_id ON public.sale(customer_id);
CREATE INDEX fkIdx_sale__sales_point_id ON public.sale(sales_point_id);
CREATE INDEX fkIdx_sale__employee_id ON public.sale(employee_id);

--  public.sale_product
drop table public.sale_product cascade;
CREATE TABLE public.sale_product(
 sale_id    bigint,
 product_id bigint,
 count     bigint NOT NULL,
 CONSTRAINT fk_sale_product__sale_id FOREIGN KEY ( sale_id ) REFERENCES public.sale(sale_id),
 CONSTRAINT fk_sale_product__product_id FOREIGN KEY ( product_id ) REFERENCES public.product(product_id)
);
CREATE UNIQUE INDEX PK_SaleProduct ON public.sale_product(
 sale_id,
 product_id
);
CREATE INDEX fkIdx_340 ON public.sale_product(sale_id);
CREATE INDEX fkIdx_353 ON public.sale_product(product_id);

--  com.pyankoff.databases.Application
drop table public.application cascade;

 CREATE TABLE application(
 application_id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 sales_point_id  bigint,
 date          timestamp NOT NULL,
 CONSTRAINT fk_application__sales_point FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id)
);
CREATE INDEX fkIdx_357 ON application( sales_point_id);

-- application_product
drop table public.application_product cascade;
CREATE TABLE application_product(
 application_id bigint,
 product_id     bigint,
 count         int NOT NULL,
 CONSTRAINT fk_application_product__application FOREIGN KEY ( application_id ) REFERENCES application ( application_id ),
 CONSTRAINT fk_application_product__product FOREIGN KEY ( product_id ) REFERENCES public.product ( product_id )
);
CREATE UNIQUE INDEX pk_product_application ON application_product(
 application_id,
 product_id
);
CREATE INDEX fkIdx_application_product__application_id ON application_product(application_id);
CREATE INDEX fkIdx_application_product__product_id ON application_product(product_id);

-- public.delivery - поставка от заказчика
drop table public.delivery cascade;
CREATE TABLE public.delivery(
 delivery_id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 supplier_id bigint,
 date       timestamp NOT NULL
);
--  public.delivery_distribution
drop table public.delivery_distribution cascade;
CREATE TABLE public.delivery_distribution(
 delivery_id   bigint,
 product_id    bigint,
 sales_point_id bigint,
 count        bigint NOT NULL,
 CONSTRAINT fk_delivery_distribution__delivery_id FOREIGN KEY ( delivery_id ) REFERENCES public.delivery(delivery_id),
 CONSTRAINT fk_delivery_distribution__product_id FOREIGN KEY ( product_id ) REFERENCES public.product(product_id),
 CONSTRAINT fk_delivery_distribution__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id)
);
CREATE UNIQUE INDEX pk_delivery_distribution ON public.delivery_distribution (
 delivery_id,
 product_id,
 sales_point_id
);
CREATE INDEX fkIdx_delivery_distribution__delivery_id ON public.delivery_distribution( delivery_id);
CREATE INDEX fkIdx_delivery_distribution__product_id ON public.delivery_distribution( product_id);
CREATE INDEX fkIdx_delivery_distribution__sales_point_id ON public.delivery_distribution ( sales_point_id);

--  public.OrderProduct
drop table public.order_product cascade;
CREATE TABLE public.order_product(
 order_id   bigint,
 product_id bigint,
 count     integer NOT NULL,
 CONSTRAINT fk_order_product__order_id FOREIGN KEY ( order_id ) REFERENCES public.order(order_id),
 CONSTRAINT fk_order_product__product_id FOREIGN KEY ( product_id ) REFERENCES public.product(product_id)
);
CREATE UNIQUE INDEX pkInd_order_product ON public.order_product(
 order_id,
 product_id
);
CREATE INDEX fkIdx_order_product__order_id ON public.order_product( order_id);
CREATE INDEX fkIdx_order_product__product_id ON public.order_product( product_id);

-- public.section
drop table public.section cascade;
CREATE TABLE public.section(
 section_id bigint primary key GENERATED ALWAYS AS IDENTITY (start 1 ),
 floor     integer NOT NULL
);

-- section_manager
drop table public.section_manager cascade;
CREATE TABLE section_manager(
 employee_id bigint,
 section_id  bigint,
 CONSTRAINT fk_section_manager__employee_id FOREIGN KEY ( employee_id ) REFERENCES employee ( employee_id ),
 CONSTRAINT fk_section_manager__section_id FOREIGN KEY ( section_id ) REFERENCES section ( section_id )
);
CREATE UNIQUE INDEX fkIdx_section_manager__emloyee_id ON section_manager(employee_id);
CREATE INDEX fkIdx_section_manager__section_id ON section_manager(section_id);

--  public.storage
drop table public.storage cascade;
CREATE TABLE public.storage(
 sales_point_id bigint,
 product_id    bigint,
 count        integer,
 price         money,
 CONSTRAINT fk_storage__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id),
 CONSTRAINT fk_storage__product_id FOREIGN KEY ( product_id ) REFERENCES public.product(product_id)
);
CREATE UNIQUE INDEX pk_storage ON public.storage(
 sales_point_id,
 product_id
);
CREATE INDEX fkIdx_storage__sales_point_id ON public.storage(sales_point_id);
CREATE INDEX fkIdx_storage__product_id ON public.storage(product_id);

-- public.supplier_price_list
drop table public.supplier_price_list cascade;
CREATE TABLE public.supplier_price_list(
 supplier_id bigint,
 product_id  bigint,
 price      integer NOT NULL,
 CONSTRAINT fk_supplier_price_list__supplier_id FOREIGN KEY ( supplier_id ) REFERENCES public.supplier(supplier_id),
 CONSTRAINT fk_supplier_price_list__product_id FOREIGN KEY ( product_id ) REFERENCES public.product(product_id)
);
CREATE UNIQUE INDEX pk_supplier_price_list ON public.supplier_price_list(
 supplier_id,
 product_id
);
CREATE INDEX fkIdx_supplier_price_list__supplier_id ON public.supplier_price_list(supplier_id);
CREATE INDEX fkIdx_supplier_price_list__product_id ON public.supplier_price_list(product_id);

--========================================================================================================
