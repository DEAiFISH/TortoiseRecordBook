drop database if exists TURTLE_RECORD_BOOK;
create database TURTLE_RECORD_BOOK;

use TURTLE_RECORD_BOOK;

drop table if exists USER;
create table USER
(
    U_ID         varchar(128) primary key comment 'UUID生成ID',
    U_ACCOUNT    varchar(128)   not null unique,
    U_PASSWD     varchar(128)   not null,
    U_HEAD_TILTS varchar(1024) not null comment '头像图片路径地址'
);

drop table if exists TORTOISE;
create table TORTOISE
(
    U_ID         varchar(32)   not null comment '主人的ID号',
    T_ID         varchar(32) primary key comment 'UUID生成ID',
    FOREIGN KEY (U_ID) REFERENCES USER (U_ID),
    T_NAME       varchar(10)   not null,
    T_FIRST_TIME DATE          null,
    T_HEAD_TILTS varchar(1024) not null comment '头像图片路径地址',
    T_STATUS     TINYINT       not null comment '使用整数值来表示不同的枚举常量',
    T_KIND       TINYINT       not null comment '使用整数值来表示不同的枚举常量',
    T_MEMO       text          null,
    T_PRICE      float default 0.0
);

drop table if exists RECORDING_INFORMATION;
create table RECORDING_INFORMATION
(
    T_ID     varchar(32)             not null,
    I_ID     varchar(32) primary key not null,
    FOREIGN KEY (T_ID) REFERENCES TORTOISE (T_ID),
    I_IMG    varchar(2048)           null comment '储存路径地址，目标是个文件夹，最多9张',
    I_DATE   date                    not null,
    I_REMARK text                    null,
    I_SIZE   float                   null comment '背甲长度（cm）',
    I_WEIGHT float                   null comment '体重（g）'
);

select *
from USER;
select *
from TORTOISE;
select *
from RECORDING_INFORMATION;