CREATE TABLE company( 
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values(1, 'Apple');
insert into company(id, name) values(2, 'Google');
insert into company(id, name) values(3, 'Amazone');
insert into company(id, name) values(4, 'Ozon');
insert into company(id, name) values(5, 'Fonbet');

insert into person(id, name, company_id) values(1, 'Tom', 3);
insert into person(id, name, company_id) values(2, 'Bob', 3);
insert into person(id, name, company_id) values(3, 'Ann', 2);
insert into person(id, name, company_id) values(4, 'Tommi', 2);
insert into person(id, name, company_id) values(5, 'Kent', 4);
insert into person(id, name, company_id) values(6, 'Kot', 5);

select p.name, c.name
from person p
left join company c
on c.id = p.company_id
where p.company_id != 5;

select company.name, count(person) as c 
	from company 
	inner join person 
	on (company.id = person.company_id) 
	group by company.name
	having count(person.name) = max(person.id);
