package com.pyankoff.databases;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class DataPopulator {

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

    public void run() throws IOException {
        addSuppliers();
        addCustomers();
        addArea();
        addOrders();
        addProducts();
        addSalesPoints();
        addEmployee();
        addSellers();
        addSales();
        addSaleProduct();
        addApplication();
        addApplicationProduct();
        addDelivery();
        addDeliveryDictributions();
        addOrderProduct();
        addSections();
        addSectionManager();
        addStorage();
        addSupplierPriseList();
    }

    private void addSupplierPriseList() throws IOException {
        log.info("inserting into supplier_price_list");
        for(int i = 0; i < 500; i++){
            jdbcTemplate.update("insert into supplier_price_list(supplier_id, product_id, price) values (?, ?, ? ) on conflict do nothing",
                    intFromRange(1, 60),//supplier
                    intFromRange(1, 12),//product
                    BigDecimal.valueOf(500 * (i % 14))//price
            );
        }
    }

    private void addStorage() {
        log.info("inserting into storage");
        for(int i = 0; i < 100; i++){
            jdbcTemplate.update("insert into storage(sales_point_id, product_id, count, price) values (?, ?, ?, ? ) on conflict do nothing",
                    intFromRange(1, 10),//salespoint
                    intFromRange(1, 11),//product
                    intFromRange(0, 500),//count
                    BigDecimal.valueOf(500 * (i % 14))
            );
        }
    }
    private void addSectionManager() {
        log.info("inserting into section_manager");
        for(int i = 0; i < 100; i++){
            jdbcTemplate.update("insert into section_manager(employee_id, section_id) values (?,?) on conflict do nothing",
                    intFromRange(14, 100),
                    intFromRange(1, 10)
            );
        }
    }

    private void addSections() {
        log.info("inserting into section");
        for(int i = 0; i < 100; i++){
            jdbcTemplate.update("insert into section(floor) values (?) on conflict do nothing",
                    intFromRange(1, 3)
            );
        }
    }

    private void addOrderProduct() {
        log.info("inserting into order_product");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 100; i++){
            jdbcTemplate.update("insert into order_product(order_id, product_id, count) values (?, ?, ?) on conflict do nothing",
                    intFromRange(1, 10),
                    intFromRange(1, 10),
                    intFromRange(1, 100)
            );
        }
    }

    private void addDeliveryDictributions(){
        log.info("inserting into delivery_distribution");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 100; i++){
            jdbcTemplate.update("insert into delivery_distribution(delivery_id, product_id, sales_point_id, count) values (?, ?, ? ,?) on conflict do nothing",
                    intFromRange(1, 10),
                    intFromRange(1, 12),
                    intFromRange(1, 40),
                    intFromRange(1, 300)
            );
        }
    }

    private void addDelivery(){
        log.info("inserting into delivery");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 10; i++){
            jdbcTemplate.update("insert into delivery(supplier_id, date) values (?, ?) on conflict do nothing",
                    intFromRange(1, 60),
                    date.plusDays(3*i)
            );
        }
    }

    private void addApplicationProduct(){
        log.info("inserting into application_product");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 10; i++){
            jdbcTemplate.update("insert into application_product(application_id, product_id, count) values (?, ?, ?) on conflict do nothing",
                    intFromRange(1, 10),
                    intFromRange(1, 12),
                    intFromRange(100, 500)
            );
        }
    }

    private void addApplication(){
        log.info("inserting into application");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 10; i++){
            jdbcTemplate.update("insert into application(sales_point_id, date) values (?, ?) on conflict do nothing",
                    intFromRange(1, 10),
                    date.plusHours(3*i));
        }
    }

    private void addSales(){
        log.info("inserting into sales");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 10; i++){
            jdbcTemplate.update("insert into sale(customer_id, sales_point_id, employee_id, date) values (?, ?, ?, ?) on conflict do nothing",
                    intFromRange(1, 10),
                    intFromRange(1, 10),
                    intFromRange(3, 13),
                    date.minusDays(2*i));
        }
    }

    private void addSaleProduct() throws IOException {
        log.info("inserting into sale_product");
        for(int i = 0; i < 10; i++){
            jdbcTemplate.update("insert into sale_product(sale_id, product_id, count) values (?, ?, ?) on conflict do nothing",
                    intFromRange(1, 10),
                    intFromRange(1, 10),
                    intFromRange(1, 10));
        }
    }

    private void addSellers(){
        log.info("inserting employees");

        for(int i = 3; i < 13; i++){
            jdbcTemplate.update("insert into seller(employee_id) values (?) on conflict do nothing",
                    i);
        }
    }

    private void addSuppliers()  throws IOException {
        log.info("inserting supplier");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        Collections.reverse(Arrays.asList(names));

        for(int i = names.length * 2/ 3; i < names.length; i++){
            jdbcTemplate.update("insert into supplier(company_name) values (?) on conflict do nothing",
                    names[i]
            );
        }
    }
    
    public void addCustomers() throws IOException {
        log.info("inserting customers");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        for(int i = 0; i < names.length / 3; i++){
            jdbcTemplate.update("insert into customer(customer_name) values (?) on conflict do nothing",
                    names[i]);
        }
    }

    private void addArea() throws IOException {
        log.info("inserting area");
        for(int i = 0; i < 30; i++){
            jdbcTemplate.update("insert into area(square, rent_price, municipal_services_price, stall_count) values (?, ?, ?, ?) on conflict do nothing",
                    intFromRange(5, 100),
                    intFromRange(1000, 40000),
                    intFromRange(1000, 15000),
                    intFromRange(1, 100));
        }
    }

    public void addSalesPoints(){
        log.info("inserting sales points");
        String[] types = new String[]{"Магазин", "Универмаг", "Киоск", "Палатка"};
        for(int i = 0; i < 40; i++){
            String type = types[intFromRange(0, types.length)];
            jdbcTemplate.update("insert into sales_point(type, area_id) values (?, ?) on conflict do nothing",
                    type,
                    "Палатка".equals(type)?null: intFromRange(1, 30));
        }
    }

    public void addEmployee() throws IOException {
        log.info("inserting employees");
        String string = resourceLoader.load("origins/names.txt");
        String[] names = string.split(" ");
        Collections.reverse(Arrays.asList(names));

        for(int i = names.length / 3; i < names.length * 2 / 3; i++){
            jdbcTemplate.update("insert into employee(name, sales_point_id, salary) values (?, ?, ?) on conflict do nothing",
                    names[i],
                    intFromRange(1, 10),
                    BigDecimal.valueOf(5000 * (i % 14)));
        }
    }

    private void addProducts() throws IOException {
        log.info("inserting products");
        String string = resourceLoader.load("origins/products.txt");
        String[] products = string.split("\n");
        for(String product :Arrays.asList(products)){
            jdbcTemplate.update("insert into product(product_name) values (?) on conflict do nothing",
                    product);
        }
    }

    private void addOrders() {
        log.info("inserting orders");
        LocalDateTime date = LocalDateTime.now();
        for(int i = 0; i < 30; i++){
            jdbcTemplate.update("insert into \"order\" (date) values ( ? ) on conflict do nothing",
                date.minusDays(i)
            );
        }
    }
}
