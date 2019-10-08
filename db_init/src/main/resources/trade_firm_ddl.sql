--Supplier
CREATE TABLE public.supplier(
 supplier_id  bigint primary key,
 company_name varchar(256) NOT NULL
);
-- public.customer
CREATE TABLE public.customer(
 customer_id   integer NOT NULL GENERATED ALWAYS AS IDENTITY (start 1 ),
 customer_name varchar(50) NOT NULL,
 CONSTRAINT pk_customer PRIMARY KEY ( customer_id )
);
-- public.area
CREATE TABLE public.area(
 area_id                 bigint primary key,
 rent_price              integer NOT NULL,
 municipal_services_price integer NOT NULL,
 stall_count             integer NOT NULL
);
--  public.order
CREATE TABLE public.order(
 order_id   bigint primary key,
 date       timestamp NOT NULL
);
-- public.product
CREATE TABLE public.product(
 product_id   bigint NOT NULL primary key,
 product_name varchar(50) NOT NULL
);
--  public.sales_point
CREATE TABLE public.sales_point(
 sales_point_id bigint primary key,
 type         varchar(50) NOT NULL,
 area_id       bigint NULL,
 CONSTRAINT fk_sales_point__area_id FOREIGN KEY ( area_id ) REFERENCES public.area(area_id)
);
CREATE INDEX fkIdx_sales_point__area_id ON public.sales_point( area_id);
--  public.employee
CREATE TABLE public.employee(
 employee_id   bigint primary key,
 name         varchar(256) NOT NULL,
 sales_point_id bigint NOT NULL,
 CONSTRAINT fk_employee__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id)
);
CREATE INDEX fkIdx_employee__sales_point_id ON employee( sales_point_id);
-- Seller
CREATE TABLE seller(
 employee_id bigint primary key,
 CONSTRAINT fk_seller__employee_id FOREIGN KEY ( employee_id ) REFERENCES employee ( employee_id )
);

-- public.sale
CREATE TABLE public.sale(
 sale_id       bigint primary key,
 customer_id   integer, -- покупатель может быть не указан
 sales_point_id bigint NOT NULL,
 employee_id   bigint NOT NULL,
 date         timestamp NOT NULL,
 CONSTRAINT fk_sale__customer_id FOREIGN KEY ( customer_id ) REFERENCES public.customer(customer_id),
 CONSTRAINT fk_sale__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id),
 CONSTRAINT fk_sale__employee_id FOREIGN KEY ( employee_id ) REFERENCES seller(employee_id)
);
CREATE INDEX fkIdx_sale__customer_id ON public.sale(customer_id);
CREATE INDEX fkIdx_sale__sales_point_id ON public.sale(sales_point_id);
CREATE INDEX fkIdx_sale__employee_id ON public.sale(employee_id);
--  public.sale_product
CREATE TABLE public.sale_product(
 sale_id    bigint NOT NULL,
 product_id bigint NOT NULL,
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
--  Application
CREATE TABLE application(
 application_id bigint primary key,
 sales_point_id  bigint NOT NULL,
 date          timestamp NOT NULL,
 CONSTRAINT fk_application__sales_point FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id)
);
CREATE INDEX fkIdx_357 ON application( sales_point_id);
-- application_product
CREATE TABLE application_product(
 application_id bigint NOT NULL,
 product_id     bigint NOT NULL,
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
CREATE TABLE public.delivery(
 delivery_id bigint primary key,
 date       timestamp NOT NULL
);
--  public.DeliveryDistribution
CREATE TABLE public.delivery_distribution(
 delivery_id   bigint NOT NULL,
 count        bigint NOT NULL,
 product_id    bigint NOT NULL,
 sales_point_id bigint NOT NULL,
 CONSTRAINT FK_230 FOREIGN KEY ( delivery_id ) REFERENCES public.delivery(delivery_id),
 CONSTRAINT FK_233 FOREIGN KEY ( product_id ) REFERENCES public.product(product_id),
 CONSTRAINT FK_236 FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id)
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
CREATE TABLE public.order_product(
 order_id   bigint NOT NULL,
 product_id bigint NOT NULL,
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
--  Section
CREATE TABLE section(
 section_id bigint primary key,
 floor     integer NOT NULL
);
-- section_manager
CREATE TABLE section_manager(
 employee_id bigint primary key,
 section_id  bigint NOT NULL,
 CONSTRAINT fk_section_manager__employee_id FOREIGN KEY ( employee_id ) REFERENCES employee ( employee_id ),
 CONSTRAINT fk_section_manager__section_id FOREIGN KEY ( section_id ) REFERENCES section ( section_id )
);
CREATE INDEX fkIdx_section_manager__section_id ON section_manager(section_id);
--  public.storage
CREATE TABLE public.storage(
 sales_point_id bigint NOT NULL,
 product_id    bigint NOT NULL,
 count        integer NOT NULL,
 CONSTRAINT fk_storage__sales_point_id FOREIGN KEY ( sales_point_id ) REFERENCES public.sales_point(sales_point_id),
 CONSTRAINT fk_storage__product_id FOREIGN KEY ( product_id ) REFERENCES public.product(product_id)
);
CREATE UNIQUE INDEX pk_storage ON public.storage(
 sales_point_id,
 product_id
);
CREATE INDEX fkIdx_storage__sales_point_id ON public.storage(sales_point_id);
CREATE INDEX fkIdx_storage__product_id ON public.storage(product_id);
-- public.SupplierPriceList
CREATE TABLE public.supplier_price_list(
 supplier_id bigint NOT NULL,
 product_id  bigint NOT NULL,
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
