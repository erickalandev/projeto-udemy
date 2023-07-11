create table tb_cidade (
	id_cidade bigint not null primary key,
	nome varchar(50) not null,
	qtd_habitantes bigint
);

insert into tb_cidade
	(id_cidade, nome, qtd_habitantes)
values
	(1, 'Sao Paulo', 12396372),
	(2, 'Rio de Janeiro', 1000000),
	(3, 'Fortaleza', 1212323),
	(4, 'Salvador', 2322323),
	(5, 'Belo Horizonte', 1908354),
	(6, 'Porto Alegre', 3232323),
	(7, 'Porto Velho', 9098999),
	(8, 'Palmas', 6374932),
	(9, 'Recife', 1234784),
	(10, 'Natal', 9847236),
	(11, 'Brasilia', 5454343);