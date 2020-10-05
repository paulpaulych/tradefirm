## Реализация приложения в архитектуре клиент-сервер.

Индивидуальный проект 6-го  семестра представляет собой полноценное приложение, работающее с базой данных.
Для выбранного проекта студент разрабатывает структуру базы данных и реализует приложение в архитектуре клиент-сервер,
выполняющее операции внесения данных в базу данных, редактирование данных и запросы, указанные в проекте.
Клиентская часть реализуется на языке программирования высокого уровня.
В описании проекта дана обобщённая пользовательская спецификация приложения.
Спецификация не предполагает оптимального определения структур данных,
но задает полный перечень хранимой в базе данных информации и выполняемых программой функций.
Данные, которыми будут наполняться таблицы БД, не должны быть наподобие следующих: поле Ф.И.О. –«фывфыв», поле «Описание работы»  - «апкцуку».
Т.е. все данные по содержанию должны соответствовать названиям соответствующих полей таблиц БД. 
В таблицах должна быть информация о 5 -7-и объектах каждого вида.

**Разработка проекта предполагает выполнение следующих этапов:**
1.Разработка структуры базы данных (серверная часть)
        1.1 Проектирование инфологической модели задачи. Определение сущностей, атрибутов сущностей, идентифицирующих атрибутов, связей между сущностями. При проектировании должны учитываться требования гибкости структур для выполнения перечисленных функций и не избыточного хранения данных.
        1.2 Проектирование схемы базы данных: описание схем таблиц, типов (доменов) атрибутов, определение ограничений целостности.
        1.3 Реализация триггеров и хранимых процедур.
1. Разработка приложения (клиентская часть) 
        2.1 Среда реализации: среда одного из языков программирования: C++,  C#, Java, Python и т.д.
        2.2 Настройка Клиента для соединения с базой данных: конфигурация BDE Administrator.
        2.3 Проектирование и реализация нескольких видов форм. 
    Формы для ввода, формы редактирования данных и формы для поиска данных по запросам.

### Прием проекта
При приеме проекта требуется предоставить:
    - файл с sql-скриптами, содержащими создание, наполнение таблиц. 
Также требуются select-скрипты, используемые в Клиенте;
    - файлы Клиента, содержащие программный код с подробными комментариями.
Проект не принимается в следующих случаях:
    - несамостоятельная реализация задания;
    - не выполнение задания в отведенный для этого срок;
    - недостаточное (менее 50%) выполнение частей проекта

Проект выполнено на:
    - «5», если полностью корректно спроектирована структура БД и реализован Клиент;
    - «4», если есть некоторые неточности в структуре БД и в Клиенте, но в целом все реализовано;
    - «3», если частично (более 50%)  выполнены обе части задания.

Процент выполнения определяется "на глаз".

Для того чтобы проект был безусловно сочтён "полностью и корректно выполненным", должны быть реализованы на 100% следующие пункты:
1. Аккуратный и "идиоматичный" UI. Не надо красоты и супер UX, но глаз не должен вытекать и структура и функциональность форм должна быть стандартной для вашей платформы. Лучше предварительно нарисовать на бумаге формы и согласовать "дизайн" со мной.
1. Автоматическое создание схемы БД и наполнение первичным данными (для JVM - liquibase, flyway)
1. Есть формы ввода, изменения и удаления всех таблиц
1. Есть формы вывода всех запросов
1. Есть как минимум одна «идеальная» форма ввода:
    1. Есть валидация данных
    1. Справочные и связанные сущности выбираются из списка
    1. Есть возможность из «идеальной» формы перейти на форму создания ссылаемой сущности, создать ссылаемую сущность, Вернуться на «идеальную» форму (с сохранением уже введённых данных!) и выбрать вновь созданную сущность
    1. Каким-то образом надо применить транзакции - при сохранении формы выполняется более одного insert-update-delete запроса в рамках одной транзакции.
1. Есть как минимум одна «идеальная» форма вывода:
    1. Есть возможность пагинации
    1. Есть возможность сортировки по любому из полей
    1. Есть возможность фильтрации по любому из полей. Для строковых полей должна быть фильтрация по подстроке
1. Есть возможность пакетной вставки. Тем кто использует JPA: написать в сопроводительном письме какие параметры управляют пакетной вставкой (batch insert/update) и на что они влияют