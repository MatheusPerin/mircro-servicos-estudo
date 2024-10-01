insert into category (id, description) values (1000, 'Comic Books');
insert into category (id, description) values (2000, 'Movies');
insert into category (id, description) values (3000, 'Books');

insert into supplier (id, name) values (1000, 'Panic Comics');
insert into supplier (id, name) values (2000, 'Amazon');

insert into product (id, name, supplierid, categoryid, quantityavailable, created) values (1000, 'Crise nas infinitas terras', 1000, 1000, 10, current_timestamp);
insert into product (id, name, supplierid, categoryid, quantityavailable, created) values (2000, 'Interestelar', 2000, 2000, 5, current_timestamp);
insert into product (id, name, supplierid, categoryid, quantityavailable, created) values (3000, 'Harry Potter e a pedra filosofal', 2000, 3000, 3, current_timestamp);