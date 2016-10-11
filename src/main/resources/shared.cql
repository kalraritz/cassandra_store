create KEYSPACE thehood WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

use thehood;

create table warehouse(
w_id int PRIMARY KEY,
w_name text,
w_street_1 text,
w_street_2 text,
w_city text,
w_state text,
w_zip text,
w_tax double
);

create table district(
d_w_id int,
d_id int,
d_name text,
d_street_1 text,
d_street_2 text,
d_city text,
d_state text,
d_zip text,
d_tax double,
primary key (d_w_id, d_id)
);

create table stock(
s_w_id int,
s_i_id int,
s_dist_01 text,
s_dist_02 text,
s_dist_03 text,
s_dist_04 text,
s_dist_05 text,
s_dist_06 text,
s_dist_07 text,
s_dist_08 text,
s_dist_09 text,
s_dist_10 text,
s_data text,
s_i_name text,
s_i_im_d int,
s_i_im_price double,
s_i_data text,
primary key(s_w_id, s_i_id)
);

create table customer(
c_w_id int,
c_d_id int,
c_id int,
c_first text,
c_middle text,
c_last text,
c_street_1 text,
c_street_2 text,
c_city text,
c_state text,
c_zip text,
c_phone text,
c_since timestamp,
c_credit text,
c_credit_lim double,
c_discount double,
c_data text,
primary key (c_w_id, c_d_id, c_id)
);

create table customer_data(
c_w_id int,
c_d_id int,
c_id int,
c_balance double,
c_ytd_payment double,
c_payment_cnt int,
c_delivery_cnt int,
primary key (c_w_id, c_d_id, c_id, c_balance))
with clustering order by (c_d_id asc, c_id asc, c_balance desc);

create table next_order(
no_w_id int,
no_d_id int,
no_d_next_o_id int,
no_w_ytd double static,
no_d_ytd double,
primary key (no_w_id, no_d_id)
);

create table stock_level_transaction(
s_w_id int,
s_i_id int,
s_quantity double,
s_ytd double,
s_order_cnt int,
s_remote_cnt int,
primary key(s_w_id, s_i_id)
);

create type item (
    i_id int,
    ol_number int,
    supply_w_id int,
    i_amount double,
    i_quantity double,
    i_delivery_d timestamp,
    i_dist_info text
);

create table new_order_transaction(
o_w_id int,
o_d_id int,
o_id int,
o_c_id int,
o_entry_d timestamp,
o_carrier_id int,
o_ol_cnt double,
o_all_local double,
o_items set<frozen <item>>,
primary key(o_w_id, o_d_id, o_id))
with clustering order by(o_d_id asc, o_id asc);

create table order_status_transaction(
os_w_id int,
os_d_id int,
os_c_id int,
os_o_id int,
os_c_balance double,
os_o_entry_d timestamp,
os_o_carrier_id int,
os_o_items set<frozen <item>>,
primary key(os_w_id, os_d_id, os_c_id, os_o_id)
);

create table sql_warehouse(
w_id int PRIMARY KEY,
w_name text,
w_street_1 text,
w_street_2 text,
w_city text,
w_state text,
w_zip text,
w_tax double,
w_ytd double);

create table sql_district(
d_w_id int,
d_id int,
d_name text,
d_street_1 text,
d_street_2 text,
d_city text,
d_state text,
d_zip text,
d_tax double,
d_ytd double,
d_next_o_id int,
primary key (d_w_id, d_id)
);

create table sql_customer(
c_w_id int,
c_d_id int,
c_id int,
c_first text,
c_middle text,
c_last text,
c_street_1 text,
c_street_2 text,
c_city text,
c_state text,
c_zip text,
c_phone text,
c_since timestamp,
c_credit text,
c_credit_lim double,
c_discount double,
c_balance double,
c_ytd_payment double,
c_payment_cnt int,
c_delivery_cnt int,
c_data text,
primary key (c_w_id, c_d_id, c_id)
); 

create table sql_order(
o_w_id int,
o_d_id int,
o_id int, 
o_c_id int,
o_carrier_id int,
o_ol_cnt double,
o_all_local double,
o_entry_d timestamp,
primary key (o_w_id, o_d_id, o_id)
);

create table sql_item(
i_id int,
i_name text,
i_price double,
i_im_id int,
i_data text,
primary key (i_id)
);

create table sql_orderline(
ol_w_id int,
ol_d_id int,
ol_o_id int,
ol_number int,
ol_i_id int,
ol_delivery_d timestamp,
ol_amount double,
ol_supply_w_id int,
ol_quantity double,
ol_dist_info text,
primary key (ol_w_id, ol_d_id, ol_o_id, ol_number)
);

create table sql_stock(
s_w_id int,
s_i_id int,
s_quantity double,
s_ytd double,
s_order_cnt int,
s_remote_cnt int,
s_dist_01 text,
s_dist_02 text,
s_dist_03 text,
s_dist_04 text,
s_dist_05 text,
s_dist_06 text,
s_dist_07 text,
s_dist_08 text,
s_dist_09 text,
s_dist_10 text,
s_data text,
primary key(s_w_id, s_i_id)
);

copy sql_warehouse from '/home/a013/a0134536/D8-data/warehouse.csv';

copy sql_district(d_w_id, d_id, d_name, d_street_1, d_street_2, d_city, d_state, d_zip, d_tax, d_ytd, d_next_o_id) from '/home/a013/a0134536/D8-data/district.csv';

copy sql_customer (
c_w_id,
c_d_id,
c_id,
c_first,
c_middle,
c_last,
c_street_1,
c_street_2,
c_city,
c_state,
c_zip,
c_phone,
c_since,
c_credit,
c_credit_lim,
c_discount,
c_balance,
c_ytd_payment,
c_payment_cnt,
c_delivery_cnt,
c_data) from '/home/a013/a0134536/D8-data/customer.csv';

copy sql_order(
o_w_id,
o_d_id,
o_id,
o_c_id,
o_carrier_id,
o_ol_cnt,
o_all_local,
o_entry_d) from '/home/a013/a0134536/D8-data/order.csv' with NULL='null';

copy sql_item(
i_id,
i_name,
i_price,
i_im_id,
i_data) from '/home/a013/a0134536/D8-data/item.csv';

copy sql_orderline(
ol_w_id,
ol_d_id,
ol_o_id,
ol_number,
ol_i_id,
ol_delivery_d,
ol_amount,
ol_supply_w_id,
ol_quantity,
ol_dist_info) from '/home/a013/a0134536/D8-data/order-line.csv' with NULL='null';

copy sql_stock(
s_w_id,
s_i_id,
s_quantity,
s_ytd,
s_order_cnt,
s_remote_cnt,
s_dist_01,
s_dist_02,
s_dist_03,
s_dist_04,
s_dist_05,
s_dist_06,
s_dist_07,
s_dist_08,
s_dist_09,
s_dist_10,
s_data) from '/home/a013/a0134536/D8-data/stock.csv';

create type item (
ol_i_id int,
i_name text,
ol_number int,
ol_s_w_id int,
ol_quantity int,
ol_delivery_d timestamp,
ol_dist_info text,
ol_amount double;
);

create table new_order_transaction(
o_w_id int,
o_d_id int,
o_id int,
o_c_id int;
o_entry_d timestamp,
o_carrier_id int,
o_ol_cnt int;
o_all_local double,
o_entry_d timestamp,
o_items set<item>,
primary key((o_w_id, o_d_id), o_id) 
)

create table warehouse(
w_id int PRIMARY KEY,
w_name text,
w_street_1 text,
w_street_2 text,
w_city text,
w_state text,
w_zip text,
w_tax double);

create table district(
d_w_id int,
d_id int,
d_name text,
d_street_1 text,
d_street_2 text,
d_city text,
d_state text,
d_zip text,
d_tax double,
primary key (d_w_id, d_id)
);

create table customer(
c_w_id int,
c_d_id int,
c_id int,
c_first text,
c_middle text,
c_last text,
c_street_1 text,
c_street_2 text,
c_city text,
c_state text,
c_zip text,
c_phone text,
c_since timestamp,
c_credit text,
c_credit_lim double,
c_discount double,
c_data text,
primary key (c_w_id, c_d_id, c_id)
); 

create table stock(
s_w_id int,
s_i_id int,
s_dist_01 text,
s_dist_02 text,
s_dist_03 text,
s_dist_04 text,
s_dist_05 text,
s_dist_06 text,
s_dist_07 text,
s_dist_08 text,
s_dist_09 text,
s_dist_10 text,
s_data text,
s_i_name text,
s_i_im_d int,
s_i_price double
primary key(s_w_id, s_i_id)
);

create table stock_level_transaction(
s_w_id int,
s_d_id int,
s_i_id int,
s_quantity double,
s_ytd double,
s_order_cnt int,
s_remote_cnt int 
);

create table next_order(
w_id int,
d_id int,
d_next_o_id int,
w_ytd double,
d_ytd double,
primary key(w_id, d_id)
);

create table order_status_transaction(
o_w_id int,
o_d_id int,
o_c_id int,
c_first text,
c_middle text,
c_last text,
c_balance double,
o_id int,
o_entry_d timestamp,
o_carrier_id int,
ol_i_id set<item>,
primary key((o_w_id, o_d_id), o_c_id, o_id) 

);

-----------------
CREATE TABLE Customer (C_W_ID int, C_D_ID int, C_ID bigint, C_FIRST text,
C_MIDDLE text, C_LAST text, C_STREET_1 text, C_STREET_2 text, C_CITY text, C_STATE text, C_ZIP text,
C_PHONE text, C_SINCE timestamp, C_CREDIT text, C_CREDIT_LIM double, C_DISCOUNT double, C_DATA text,
PRIMARY KEY((C_W_ID), C_D_ID, C_ID));



CREATE TYPE item (
  OL_I_ID int,
  OL_I_NAME text,
  OL_NUMBER int,
  OL_S_W_ID int,
  OL_QUANTITY int,
  OL_DELIVERY_D timestamp,
  OL_DIST_INFO text,
  OL_AMOUNT double
);


CREATE TABLE New_Order_Transaction (
  O_W_ID int,
  O_D_ID int,
  O_ID bigint,
  O_ENTRY_D timestamp,
  O_CARRIER_ID int,
  O_C_ID bigint,
  O_OL_CNT int,
  O_ALL_LOCAL int,
  O_ITEMS set<frozen <item>>,
  PRIMARY KEY ((O_W_ID), O_D_ID, O_ID, O_ENTRY_D));



# Ritesh added dump commands
copy thehood.customer_data (c_w_id,c_d_id,c_id,c_balance,c_ytd_payment,c_payment_cnt,c_delivery_cnt) from '~/csvFiles/customerData.csv';
copy thehood.warehouse(w_id,w_name,w_street_1,w_street_2,w_city,w_state,w_zip,w_tax) from '~/csvFiles/warehouse.csv';
copy thehood.district(d_w_id, d_id,d_name,d_street_1,d_street_2,d_city,d_state,d_zip,d_tax) from '~/csvFiles/district.csv';
copy thehood.stock(s_w_id,s_i_id,s_dist_01,s_dist_02,s_dist_03,s_dist_04,s_dist_05,s_dist_06,s_dist_07,s_dist_08,s_dist_09,s_dist_10,s_data,s_i_name,s_i_im_d,s_i_im_price,s_i_data) from '~/csvFiles/stock.csv';

