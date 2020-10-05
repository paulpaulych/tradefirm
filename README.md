
# Информационная система торговой организации

## Мотивация 

Этот проект - моя курсовая по "Базам Данных" в НГУ. 

[Текст задания](./doc/task.md)

[Описание предметной области](./doc/domain.png)

[Список запросов](./doc/analytics_queries.png)

## Локальный запуск

1. поднимаем базу: docker-compose up -d
2. ./gradlew server:bootRun - база заполняется данными и запускается сервер
3. cd frontend & npm install @angular/cli & ng serve - поднимается фронт на 4200 порту

### Об интерфейсе

**Явки-пароли:**

- **admin:admin** - Админка. Там можно выполнять аналитические запросы([Список запросов](./doc/analytics_queries.png)) 
и просматривать и редактировать содержимое всех таблиц
- **user:user** - Рабочее пространство продавца в магазине. User привязан к конкретной торговой точке. 

По поводу внешнего вида гуя… можете считать, что у меня нет вкуса и никаких способностей к редактированию CSS.

Нафантазировал бизнес-процесс “заявка-поставка”:
- Продавец формирует заявку на поставку в веб-интерфейсе. 
- Сервер с фиксированной периодичностью собирает необработанные заявки, формирует и сохраняет заказ.
- Фэйковый сервис поставщиков периодически выгребает необработанные заказы, случайным образом выбирает для них поставщика и ”делает поставки”.
- В магазин приезжает курьер с поставкой, сообщает продавцу номер поставки.
- Продавец в веб-форме указывает этот номер, а также чего и сколько привез курьер


### По требованиям

Сначала читать пункт полностью, потом смотреть гуй и код:

1. Реализация триггеров и хранимых процедур: 

    В самом низу [этого файла](./db_init/src/main/resources/ddl/tradefirm_ddl.sql)
    
    Процедуру использую в [одном из запросов](./server/src/main/resources/sql/analytics/salespoint/profitability.sql)
    
    Триггер - это часть процесса обработки заказа. Когда из заявок формируется и сохраняется заказ, срабатывает триггер, который записывает факт появления нового заказа в табличку со статусами. А из этой таблички сервис поставщиков периодически выгребает необработанные заказы и делает делает поставки.
    
2. Файл с sql-скриптами, содержащими создание, наполнение таблиц:
    
    DDL-скрипты вот в [этом файле](./db_init/src/main/resources/ddl/tradefirm_ddl.sql)
    
    Наполнение данными: этим занимается модуль db-init. Генерирует много данных рандомно, но ограничения целостности не нарушает.
    Имена и названия читаемые.

3. Select-скрипты

    В гуе это страница АНАЛИТИКА у админа. Первая кнопочка.
    
    В коде: 
    
    Фронт: [admin/analytics](./frontend/src/app/admin/analytics). 
    
    Сами запросы описываются JSON-объектом в файле queries.ts. Для каждого запроса там описано: колонки для вывода, поля формы ввода параметров, тело graphql-запроса к серверу.
    Бойлерплейта меньше сделать уже невозможно, я думаю. Потратил два месяца, чтобы на добавление всех запросов потратить всего ночь.
    
    Бэк: [AnalyticsQuery.kt](server/src/main/kotlin/paulpaulych/tradefirm/admin/analytics/AnalyticsQuery.kt)
    
    Там есть ссылки на sql-файлы.
    
    Graphql-kotlin генерит graphql-схему из таких классов как AnalyticsQuery. В какой-то мере это аналог Spring-MVC-контроллера.

4. Идеальная форма ввода:

    Логинимся под user’ом -> покупки -> создать покупку. Вы на месте. 

    Там в выпадающем списке для покупателя есть кнопка “Добавить покупателя”, которая откроет окно добавления связанной сущности.
    
    Валидации чуть-чуть не хватает, надеюсь не найдете...

5. Идеальная форма вывода - практически любая таблица в админке. Почему практически? потому что фильтрация только по числовым и строковым полям. но там где она есть она получилась довольно продвинутой. Можно потыкать на названия колонок для сортировки. 

    Крупный баг заключается в том, что пагинация не останавливается на последней странице/строке.
    Оправдание типа “орм который я использую([SimpleOrm](https://github.com/paulpaulych/simple-orm)) такого еще не поддерживает” пойдёт?
    
    Фронт: [crud-tables](./frontend/src/app/admin/crud-tables)
    
    Бэк: [crud-api](server/src/main/kotlin/paulpaulych/tradefirm/admin/crudapi)
    
    Контроллер бэкенда для всех таблиц общий как и грид для вывода на фронте.
    
    По сути, чтобы добавить таблицу, нужно создать data-класс на бэке и описать набор колонок на фронте.

6. Транзакции
    - Создание заказа из необработанных заявок. Класс [OrderCreateService](./server/src/main/kotlin/paulpaulych/tradefirm/sellerapi/order/OrderCreateService.kt)
    - Создание покупки. Класс [SaleMutation](./server/src/main/kotlin/paulpaulych/tradefirm/sellerapi/sale/SaleMutation.kt)
    - Аналитических запросы(см. п.3). 
    
7. Пакетная вставка там же, где и идеальная форма вывода. 

## Технологии

**Фронт**: Angular 9 + Apollo-GraphQL

**Бэк**: 

- Kotlin/JVM
- graphql-kotlin - фреймворк который из котлин-классов генерит GraphQL API
- Spring Boot: 
    - WebFlux - его под капотом использует graphql-kotlin, поэтому альтернатив особо не было
    - Security - только для аутентификации. Механизм авторизации свой на своих аннотациях
    - JDBC - его использует SimpleORM
- [SimpleORM](https://github.com/paulpaulych/simple-orm) - очень помог для реализации универсального CRUD API.
Для поддержки спринговых транзакций пришлось написать адаптер для JdbcTemplate.
- JWT
- Postgres 12
