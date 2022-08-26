/* Populate Tables */
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Andrés','Guzmán','andres@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Felipe','Cruz','felipe@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Agustín','Soto','agustín@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Alberto','Perez','alberto@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Alejandro','González','alejandro@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Alonso','Cardenas','alonso@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Antonio','Fernandez','antonio@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Adriana','Saba','adriana@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Andrea','Calderón','andrea@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Anastasia','Mardonez','anastasia@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Bruno','Vasquez','bruno@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Bárbara','Simian','barbara@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Beatriz','Carmona','beatriz@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Belén','Andrade','belen@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Blanca','Miquel','blanca@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'Caridad','Rivas','caridad@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_ID,USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES((select nextval('seq_cliente')),'César','Tapia','cesar@correo.com','2020-01-13','');

INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Panasonic pantalla LCD',259990,NOW());
INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Sony Camara digital DSC-W320B',123490,NOW());
INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Apple ipod shuffle',149990,NOW());
INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Sony Notebook 7110',359990,NOW());
INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Hewlett Packard Multifuncional F2280',49990,NOW());
INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Bianchi Bicicleta Aro 26',69990,NOW());
INSERT INTO PRODUCTOS (PRODUCTO_ID,PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES((select nextval('seq_producto')),'Mica Comoda 5 cajones',299990,NOW());

INSERT INTO FACTURAS (FACT_ID,FACT_NAME,FACT_OBS,CLIENTE_USER_ID,FACT_CREATE) VALUES((select nextval('seq_factura')),'Factura Equipos',null,1,NOW());
INSERT INTO FACTURASITEM (FACTITEM_ID,FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES((select nextval('seq_facturaitem')),1,(select last_value from seq_factura),1);
INSERT INTO FACTURASITEM (FACTITEM_ID,FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES((select nextval('seq_facturaitem')),1,(select last_value from seq_factura),4);
INSERT INTO FACTURASITEM (FACTITEM_ID,FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES((select nextval('seq_facturaitem')),2,(select last_value from seq_factura),5);

INSERT INTO FACTURAS (FACT_ID,FACT_NAME,FACT_OBS,CLIENTE_USER_ID,FACT_CREATE) VALUES((select nextval('seq_factura')),'Factura Bicicleta','Alguna Nota',1,NOW());
INSERT INTO FACTURASITEM (FACTITEM_ID,FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES((select nextval('seq_facturaitem')),1,(select last_value from seq_factura),(select last_value from seq_producto));

INSERT INTO USUARIOS (id,username ,password, enable ) values ((select nextval('seq_usuario')),'admin','$2a$10$594134vDoEPwssMkym2fe.IRgEz1Kpr15Wsbkwc5k14GOYokOAZ4K',TRUE);
insert into roles (id,usuario_id, rol) values ((select nextval('seq_rol')), (select last_value from seq_usuario), 'ROLE_USER');
insert into roles (id,usuario_id, rol) values ((select nextval('seq_rol')), (select last_value from seq_usuario), 'ROLE_ADMIN');
INSERT INTO USUARIOS (id,username ,password, enable ) values ((select nextval('seq_usuario')),'andres','$2a$10$F36UX2xsI6QMy06mKpeaze1Ncw.M7f3EJM7c56K8CC.3pJJrQuDp6',TRUE);
insert into roles (id,usuario_id, rol) values ((select nextval('seq_rol')),(select last_value from seq_usuario), 'ROLE_USER');


