INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb21', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 500.00);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb23', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 100.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb24', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 600.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb25', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 200.00, 'DEBIT');


INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb22', 'd215b5f8-0249-4dc5-89a3-51fd148cfb43', 100.00);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb26', 'd215b5f8-0249-4dc5-89a3-51fd148cfb43', 100.00, 'CREDIT');



INSERT INTO stock.stocks(id, name, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'stock_1', TRUE);
INSERT INTO stock.stocks(id, name, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'stock_2', FALSE);

INSERT INTO stock.products(id, name, price, available)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb47', 'product_1', 25.00, FALSE);
INSERT INTO stock.products(id, name, price, available)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb48', 'product_2', 50.00, TRUE);
INSERT INTO stock.products(id, name, price, available)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb49', 'product_3', 20.00, FALSE);
INSERT INTO stock.products(id, name, price, available)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb50', 'product_4', 40.00, TRUE);

INSERT INTO stock.stock_products(id, stock_id, product_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb51', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb47');
INSERT INTO stock.stock_products(id, stock_id, product_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb52', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb48');
INSERT INTO stock.stock_products(id, stock_id, product_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb53', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb49');
INSERT INTO stock.stock_products(id, stock_id, product_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb54', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb50');
