create table reserva (
 id int(11) not null auto_increment,
 nombre_cliente varchar(100) not null,
 tipo_usuario int(1) not null,
 numero_documento int(10)  not null,
 costo_reserva int(10) not null,
 fecha_reserva date null,
 primary key (id)
);