create table tbUsuarios
(
nombreUsuario varchar2(30) not null unique,
contrasena varchar2(30) not null
);

create table tbTickets
(
numeroTicket varchar2(36) primary key,
titulo varchar2(30) not null,
descripcion varchar2(100) not null,
autor varchar2(20) not null,
emailAutor varchar2(30) not null unique,
fechaCreacion date not null,
estadoTicket varchar(10),
fechaFinalizado date
);

