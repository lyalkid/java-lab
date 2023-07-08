create table good(
    id serial primary key,
    good_name varchar (20),
    price integer,
    count integer
);

create table customer(
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20),
    company varchar(20)
);

create table shop(
    id serial primary key,
    brand varchar(20),
    adress varchar(20)
);

create table good_shop(
    id serial primary key,
    shop_id integer,
    good_id integer,
    foreign key (shop_id) references shop(id),
    foreign key (good_id) references good(id)
);

create table orders(
  id serial primary key,
  good_shop_id integer,
  customer_id integer,
  foreign key (good_shop_id) references good_shop(id),
  foreign key (customer_id) references customer(id)
);
