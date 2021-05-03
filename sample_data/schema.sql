DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product (
                                 id serial NOT NULL PRIMARY KEY,
                                 name text NOT NULL,
                                 description text,
                                 price double precision NOT NULL,
                                 category text NOT NULL,
                                 supplier text NOT NULL
);

DROP TABLE IF EXISTS public.supplier;
CREATE TABLE public.supplier (
                                id serial NOT NULL PRIMARY KEY,
                                supplier_name text NOT NULL,
                                description text,
                                UNIQUE (supplier_name)
);


DROP TABLE IF EXISTS public.category;
CREATE TABLE public.category (
                                id serial NOT NULL PRIMARY KEY,
                                category_name text NOT NULL,
                                department text,
                                description text,
                                UNIQUE (category_name)

);

DROP TABLE IF EXISTS public.order;
CREATE TABLE public.order_information (
                                id serial NOT NULL PRIMARY KEY,
                                date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                                user_id int,
                                checkout_info text,
                                items text,
                                total_price float,
                                paid boolean

);

ALTER TABLE ONLY public.order_information
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.user(id);

DROP TABLE IF EXISTS public.user;
CREATE TABLE public.user (
                              id serial NOT NULL PRIMARY KEY,
                              name text NOT NULL,
                              email text NOT NULL,
                              password text NOT NULL

);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_supplier FOREIGN KEY (supplier) REFERENCES public.supplier(supplier_name);


ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_category FOREIGN KEY (category) REFERENCES public.category(category_name);


INSERT INTO category (category_name, department, description)
VALUES ('fruit', 'fruits', 'nice fruits' );

INSERT INTO category (category_name, department, description)
VALUES ('vegetable', 'vegetables', 'nice vegetables' );

INSERT INTO supplier (supplier_name, description)
VALUES ('Vegatable World', 'Vegetables');

INSERT INTO supplier (supplier_name, description)
VALUES ('5stars Fruits', 'Fruits');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Potato', 'A root vegetable native to the Americas, a starchy tuber of the plant Solanum tuberosum', 0.50, 'vegetable', 'Vegatable World');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Tomato', 'Edible berry of the plant Solanum lycopersicum, commonly known as a tomato plant', 1.80, 'vegetable', 'Vegatable World');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Apple', 'Apple trees are cultivated worldwide and are the most widely grown species in the genus Malus. ', 2.05, 'fruit', '5stars Fruits');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Pear', 'The pear tree and shrub are a species of genus Pyrus /ˈpaɪrəs/, in the family Rosaceae.', 2.50, 'fruit', '5stars Fruits');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Mango', 'A stone fruits cultivated mostly for their edible fruit.', 6.50, 'fruit', '5stars Fruits');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Passion fruit', 'Cultivated commercially in tropical and subtropical areas for its sweet, seedy fruit.', 7.50, 'fruit', '5stars Fruits');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Lime', 'Citrus fruit, which is typically round, green in color and contains acidic juice vesicles.', 2.70, 'fruit', '5stars Fruits');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Garlic', 'It was known to ancient Egyptians and has been used as both a food flavoring and a traditional medicine.', 0.50, 'vegetable', 'Vegatable World');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Cabbage', 'a leafy green, red, or white biennial plant grown as an annual vegetable crop for its dense-leaved heads.', 1.50, 'vegetable', 'Vegatable World');

INSERT INTO product (name, description, price, category, supplier)
VALUES ('Avocado', 'The fruit of the plant, also called an avocado, is botanically a large berry containing a single large seed.', 5.50, 'fruit', '5stars Fruits');


