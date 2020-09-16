/* Populate Tables */
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Andrés','Guzmán','andres@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Felipe','Cruz','felipe@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Agustín','Soto','agustín@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Alberto','Perez','alberto@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Alejandro','González','alejandro@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Alonso','Cardenas','alonso@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Antonio','Fernandez','antonio@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Adriana','Saba','adriana@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Andrea','Calderón','andrea@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Anastasia','Mardonez','anastasia@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Bruno','Vasquez','bruno@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Bárbara','Simian','barbara@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Beatriz','Carmona','beatriz@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Belén','Andrade','belen@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Blanca','Miquel','blanca@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('Caridad','Rivas','caridad@correo.com','2020-01-13','');
INSERT INTO CLIENTES (USER_NAME,USER_LNAME,USER_EMAIL,USER_CREATE,USER_FOTO) VALUES('César','Tapia','cesar@correo.com','2020-01-13','');

INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Panasonic pantalla LCD',259990,NOW());
INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Sony Camara digital DSC-W320B',123490,NOW());
INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Apple ipod shuffle',149990,NOW());
INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Sony Notebook 7110',359990,NOW());
INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Hewlett Packard Multifuncional F2280',49990,NOW());
INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Bianchi Bicicleta Aro 26',69990,NOW());
INSERT INTO PRODUCTOS (PROD_NOMBRE,PROD_PRECIO,PROD_CREATE) VALUES('Mica Comoda 5 cajones',299990,NOW());

INSERT INTO FACTURAS (FACT_NAME,FACT_OBS,CLIENTE_USER_ID,FACT_CREATE) VALUES('Factura Equipos',null,1,NOW());
INSERT INTO FACTURASITEM (FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES(1,1,1);
INSERT INTO FACTURASITEM (FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES(1,1,4);
INSERT INTO FACTURASITEM (FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES(2,1,5);

INSERT INTO FACTURAS (FACT_NAME,FACT_OBS,CLIENTE_USER_ID,FACT_CREATE) VALUES('Factura Bicicleta','Alguna Nota',1,NOW());
INSERT INTO FACTURASITEM (FACTITEM_CANT,FACT_ID,PRODUCTO_ID) VALUES(1,2,6);


