CREATE TYPE item (
    i_id int,
    ol_number int,
    supply_w_id int,
    i_amount double,
    i_quantity double,
    i_delivery_d timestamp,
    i_dist_info text
);


CREATE TABLE new_order_transaction (
    o_w_id int,
    o_d_id int,
    o_id int,
    o_all_local double,
    o_c_id int,
    o_carrier_id int,
    o_entry_d timestamp,
    o_items set<frozen<item>>,
    o_ol_cnt double,
    PRIMARY KEY (o_w_id,o_d_id,o_id));	
	
copy new_order_transaction(o_w_id ,o_d_id,o_id,o_c_id,o_entry_d,o_carrier_id,o_ol_cnt,o_all_local,o_items) FROM 'C:\DD\D8-data\csvFiles\newOrderTransaction.csv';



create table customer_data(
c_w_id int,
c_d_id int,
c_id int,
c_balance double,
c_ytd_payment double,
c_payment_cnt int,
c_delivery_cnt int,
primary key (c_w_id, c_d_id, c_id));
copy newhood.customer_data (c_w_id,c_d_id,c_id,c_balance,c_ytd_payment,c_payment_cnt,c_delivery_cnt) from 'customerData.csv';




create table next_order(
no_w_id int,
no_d_id int,
no_d_next_o_id int,
no_w_ytd double static,
no_d_ytd double,
primary key (no_w_id, no_d_id)
);
copy next_order(no_w_id,no_d_id,no_d_next_o_id,no_w_ytd,no_d_ytd) from ''



create table stock_level_transaction(
s_w_id int,
s_i_id int,
s_quantity double,
s_ytd double,
s_order_cnt int,
s_remote_cnt int,
primary key(s_w_id, s_i_id)
);
copy newhood.stock_level_transaction(s_w_id,s_i_id,s_quantity,s_ytd,s_order_cnt,s_remote_cnt) from ''

