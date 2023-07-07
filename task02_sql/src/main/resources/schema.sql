create table good(
    id serial primary key,
    good_name varchar (20),
    price integer,
    count integer
);

create table zakazchiki(
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20),
    camponie varchar(20)
);

create table shop(
    id serial primary key,
    brand varchar(20),
    adress varchar(20)-- чекнуть как адреса проверяются
);

create table good_in_shop(
    id serial primary key,
    shop_id integer,
    good_id integer,
    foreign key (shop_id) references shop(id),
    foreign key (good_id) references good(id)
);

create table zakazi(
  id serial primary key,
  good_id integer,
  zakazchik integer,
  foreign key (good_id) references good_in_shop(id),
  foreign key (zakazchik) references zakazchiki(id)
)
