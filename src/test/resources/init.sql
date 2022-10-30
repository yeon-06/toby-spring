CREATE TABLE sqlmap
(
    key_ varchar(100) primary key,
    sql_ varchar(100) not null
);

INSERT INTO sqlmap(key_, sql_)
values ('key1', 'sql1')
     , ('key2', 'sql2')
;
