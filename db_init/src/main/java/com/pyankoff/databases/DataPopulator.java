package com.pyankoff.databases;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component
public class DataPopulator {

    private static final int SALES_POINT_COUNT = 10;
    private static final int SALES_COUNT = 500;
    private static final int PRODUCT_COUNT = 12;
    private static final int SUPPLIER_PRICE_COUNT = 700;
    private static final int ORDER_COUNT = 50;
    private static final int DELIVERY_COUNT = 100;
    private static final int ORDER_PRODUCT_COUNT = 3000;
    private static final int DELIVERY_DISTRIBUTION_COUNT = 1000;
    private static final int SALE_PRODUCT_COUNT = 4600;

    private static final int SELLERS_MIN = 101;
    private static final int SELLERS_MAX = 150;
    private static final int SELLERS_COUNT = SELLERS_MAX - SELLERS_MIN;


    private static final int CUSTOMERS_MIN = 51;
    private static final int CUSTOMERS_MAX = 100;
    private static final int CUSTOMER_COUNT = CUSTOMERS_MAX - CUSTOMERS_MIN -1;

    private static final int SUPPLIERS_MIN = 0;
    private static final int SUPPLIERS_MAX = 50;
    private static final int SUPPLIER_COUNT = SUPPLIERS_MAX - SUPPLIERS_MIN - 1;
    private static final int APPLICATION_COUNT = 100;

    private JdbcTemplate jdbcTemplate;

    private ResourceLoader resourceLoader;

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private int intFromRange(int min, int max){
        Random random = new Random();
        return Math.abs(random.nextInt())%(max - min) + min;
    }

    private BigDecimal decimalFromRange(int min, int max){
        Random random = new Random();
        int val = Math.abs(random.nextInt()) % (max - min) + min;
        return new BigDecimal(val);
    }

    public void run() throws IOException {
        addSuppliers();
        addCustomers();
        addArea();
//        addOrders();
        addProducts();
        addSalesPoints();
//        addEmployee();
        addSellers();
        addSales();
        addSaleProduct();
        addApplication();
        addApplicationProduct();
//        addDelivery();
//        addDeliveryDictributions();
//        addOrderProduct();
//        addSections();
//        addSectionManager();
        addStorage();
        addSupplierPriceList();
    }

    private void addSupplierPriceList() throws IOException {
        log.info("inserting into supplier_price_list");
        for(int i = 1; i <=SUPPLIER_PRICE_COUNT; i++){
            jdbcTemplate.update("insert into supplier_price_list(supplier_id, product_id, price) values (?, ?, ? ) on conflict do nothing",
                    intFromRange(1, SUPPLIER_COUNT),//supplier
                    intFromRange(1, PRODUCT_COUNT),//product
                    BigDecimal.valueOf(500 * (i % 14))//price
            );
        }
    }

    private void addStorage() {
        log.info("inserting into storage");
        for(int i = 1; i <=100; i++){
            jdbcTemplate.update("insert into storage(sales_point_id, product_id, count, price) values (?, ?, ?, ? ) on conflict do nothing",
                    intFromRange(1, SALES_POINT_COUNT),//salespoint
                    intFromRange(1, PRODUCT_COUNT),//product
                    intFromRange(1, 500),//count
                    decimalFromRange(30, 1500)
            );
        }
    }
    private void addSectionManager() {
        log.info("inserting into section_manager");
        for(int i = 1; i <=100; i++){
            jdbcTemplate.update("insert into section_manager(id, section_id) values (?,?) on conflict do nothing",
                    intFromRange(14, 100),
                    intFromRange(1, 10)
            );
        }
    }

    private void addSections() {
        log.info("inserting into section");
        for(int i = 1; i <=100; i++){
            jdbcTemplate.update("insert into section(floor) values (?) on conflict do nothing",
                    intFromRange(1, 3)
            );
        }
    }

//    private void addOrderProduct() {
//        log.info("inserting into order_product");
//        LocalDateTime date = LocalDateTime.now();
//        for(int i = 1; i <=ORDER_PRODUCT_COUNT; i++){
//            jdbcTemplate.update("insert into order_product(order_id, product_id, sales_point_id, count) values (?, ?, ?, ?) on conflict do nothing",
//                    intFromRange(1, ORDER_COUNT),
//                    intFromRange(1, PRODUCT_COUNT),
//                    intFromRange(1, SALES_POINT_COUNT),
//                    intFromRange(1, 100)
//            );
//        }
//    }

    private void addDeliveryDictributions(){
        log.info("inserting into delivery_distribution");
        for(int i = 1; i <=DELIVERY_DISTRIBUTION_COUNT; i++){
            jdbcTemplate.update("insert into delivery_distribution(delivery_id, product_id, sales_point_id, count) values (?, ?, ? ,?) on conflict do nothing",
                    intFromRange(1, DELIVERY_COUNT),
                    intFromRange(1, PRODUCT_COUNT),
                    intFromRange(1, SALES_POINT_COUNT),
                    intFromRange(1, 300)
            );
        }
    }

    private void addDelivery(){
        log.info("inserting into delivery");
        LocalDateTime date = now();
        for(int i = 1; i <=DELIVERY_COUNT; i++){
            jdbcTemplate.update("insert into delivery(supplier_id,order_id, date) values (?, ?, ?) on conflict do nothing",
                    intFromRange(1, 100),
                    intFromRange(1, ORDER_COUNT),
                    date.plusDays(3*i)
            );
        }
    }

    private void addApplicationProduct(){
        log.info("inserting into application_product");
        for(int i = 1; i <= APPLICATION_COUNT; i++){
            int itemsCount = intFromRange(4, 7);
            for(int j = 1; j <= itemsCount; j++){
                jdbcTemplate.update("insert into application_product(application_id, product_id, count) values (?, ?, ?) on conflict do nothing",
                        i,
                        intFromRange(1, PRODUCT_COUNT),
                        intFromRange(1, 10) * 10
                );
            }
        }
    }

    private void addApplication(){
        log.info("inserting into application");
        LocalDateTime date = now();
        for(int i = 1; i <= APPLICATION_COUNT; i++){
            jdbcTemplate.update("insert into application(sales_point_id, date) values (?, ?) on conflict do nothing",
                    intFromRange(1, SALES_POINT_COUNT),
                    date.minusMonths(3*i).minusHours(i*3));
        }
    }

    private void addSales(){
        log.info("inserting into sales");
        LocalDateTime date = now();
        for(int i = 1; i <= SALES_COUNT; i++){
            jdbcTemplate.update("insert into sale(customer_id, sales_point_id, seller_id, date) values (?, ?, ?, ?) on conflict do nothing",
                    intFromRange(1, CUSTOMER_COUNT),
                    intFromRange(1, SALES_POINT_COUNT),
                    intFromRange(1, SELLERS_COUNT),
                    date.minusDays(2*i));
        }
    }

    private void addSaleProduct() {
        log.info("inserting into sale_product");
        for(int i = 1; i <=SALE_PRODUCT_COUNT; i++){
            jdbcTemplate.update("insert into sale_product(sale_id, product_id, count) values (?, ?, ?) on conflict do nothing",
                    intFromRange(1, SALES_COUNT),
                    intFromRange(1, PRODUCT_COUNT),
                    intFromRange(1, 10));
        }
    }

    private void addSellers() throws IOException {
        log.info("inserting sellers");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        Collections.reverse(Arrays.asList(names));

        for(int i = SELLERS_MIN; i <= SELLERS_MAX ; i++){
            jdbcTemplate.update("insert into seller(name, sales_point_id, salary) values (?, ?, ?) on conflict do nothing",
                    names[i],
                    intFromRange(1, SALES_POINT_COUNT),
                    BigDecimal.valueOf(5000 * (i % 14)));
        }
    }

    private void addSuppliers()  throws IOException {
        log.info("inserting supplier");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        Collections.reverse(Arrays.asList(names));

        for(int i = SUPPLIERS_MIN; i <=SUPPLIERS_MAX; i++){
            jdbcTemplate.update("insert into supplier(company_name) values (?) on conflict do nothing",
                    names[i]
            );
        }
    }
    
    private void addCustomers() throws IOException {
        log.info("inserting customers");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        for(int i = CUSTOMERS_MIN; i <=CUSTOMERS_MAX; i++){
            jdbcTemplate.update("insert into customer(name) values (?) on conflict do nothing",
                    names[i]);
        }
    }

    private void addArea() throws IOException {
        log.info("inserting area");
        for(int i = 1; i <=30; i++){
            jdbcTemplate.update("insert into area(square, rent_price, municipal_services_price, stall_count) values (?, ?, ?, ?) on conflict do nothing",
                    intFromRange(5, 100),
                    decimalFromRange(1000, 40000),
                    decimalFromRange(1000, 15000),
                    intFromRange(1, 100));
        }
    }

    public void addSalesPoints(){
        log.info("inserting sales points");
        String[] types = new String[]{"Магазин", "Универмаг", "Киоск", "Палатка"};
        for(int i = 1; i <=SALES_POINT_COUNT; i++){
            String type = types[intFromRange(1, types.length)];
            jdbcTemplate.update("insert into sales_point(type, area_id) values (?, ?) on conflict do nothing",
                    type,
                    "Палатка".equals(type)? null: intFromRange(1, 30));
        }
    }

    private void addEmployee() throws IOException {
        log.info("inserting employees");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        Collections.reverse(Arrays.asList(names));

        for(int i = SELLERS_MIN; i <= SELLERS_MAX ; i++){
            jdbcTemplate.update("insert into employee(name, sales_point_id, salary) values (?, ?, ?) on conflict do nothing",
                    names[i],
                    intFromRange(1, SALES_POINT_COUNT),
                    BigDecimal.valueOf(5000 * (i % 14)));
        }
    }

    private void addProducts() throws IOException {
        log.info("inserting products");
        String string = resourceLoader.load("origins/products.txt");
        String[] products = string.split("\r\n");
        log.info(products[0].toString());
        for(String product :Arrays.asList(products)){
            jdbcTemplate.update("insert into product(name) values (?) on conflict do nothing",
                    product);
        }
        List<String> res = jdbcTemplate.queryForList("select name from product", String.class);
        log.info(res.get(1));
    }

    private LocalDateTime now(){
        return LocalDateTime.now(ZoneId.systemDefault());
    }

//    private void addOrders() {
//        log.info("inserting orders");
//        LocalDateTime date = LocalDateTime.now();
//        for(int i = 1; i <=ORDER_COUNT; i++){
//            jdbcTemplate.update("insert into \"order\" (date) values ( ? ) on conflict do nothing",
//                date.minusDays(i)
//            );
//        }
//    }
}
