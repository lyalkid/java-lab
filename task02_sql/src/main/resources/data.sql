insert into good(good_name, price, count)
values ('cheese', 100, 100),
       ('bread', 50, 100 ),
       ('wine', 1500, 20 ),
       ('apple', 100, 500),
       ('musli', 150, 100),
       ('milk', 90, 125),
       ('burger', 200, 50),
       ('wopper', 250, 100),
       ('coke', 100, 300),
       ('lemonade', 100, 300),
       ('water', 50, 200),
       ('tea', 90, 100);

insert into zakazchiki(first_name, last_name, componie)
values ('Azamat', 'Gaifullin', 'KPFU'),
       ('Petr', 'Pilovskiy', 'Tainer'),
       ('Ascar', 'Trovo', 'Pech');

insert into shop(brand, adress)
values ('Пятерочка', 'Восстания 80'),
       ('Spar', 'Краснокакшайская 42'),
       ('Ozon', 'Краснокакшайская 82');

insert into good_in_shop(shop_id, good_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (3, 9),
       (3, 10),
       (3, 11),
       (3, 12);

insert into zakazi(good_id, zakazchik)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 3),
       (1, 3),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 2),
       (6, 3),
       (7, 1),
       (8, 2),
       (9, 3),
       (10, 1),
       (11, 2),
       (12, 3);






