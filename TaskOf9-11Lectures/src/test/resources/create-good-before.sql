DELETE FROM product.good;
DELETE FROM product.category;

INSERT INTO product.category(id, title)
VALUES (1, 'military');

INSERT INTO product.category(id, title)
VALUES (100, 'music');

INSERT INTO product.good(id, title, rating, manufacturer, category_id)
VALUES(1, 'shortgun', 4, 'USA', 1);

INSERT INTO product.good(id, title, rating, manufacturer, category_id)
VALUES(2, 'rifle', 4, 'USA', 1);

INSERT INTO product.good(id, title, rating, manufacturer, category_id)
VALUES(3, 'HIMARS', 4, 'USA', 1);

INSERT INTO product.good(id, title, rating, manufacturer, category_id)
VALUES(4, 'ATAMS', 5, 'USA', 1);

INSERT INTO product.good(id, title, rating, manufacturer, category_id)
VALUES(5, 'Gibson', 5, 'Britain', 100);