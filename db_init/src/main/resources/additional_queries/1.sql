--1) Удалить все продукты с отрицательной ценой
-- Цена - это характеристика позиции на скаладе, а не продукта

--можно удалить позиции на складе
delete from storage s where s.price < 0;
