drop database if exists tortoise_record;
create database tortoise_record;

use tortoise_record;

drop table if exists user;
create table user
(
    user_id         varchar(32) primary key comment 'UUID',
    username        varchar(16)   not null,
    password        varchar(16)   not null,
    profile_picture varchar(1024) not null comment '头像图片路径地址',
    index idx_username (username asc)
);

drop table if exists tortoise;
create table tortoise
(
    user_id         varchar(32)             not null,
    tortoise_id     varchar(32) primary key comment 'UUID',
    name            varchar(16)             not null,
    gender          tinyint default 2       not null comment '性别 0:雌性 1:雄性 2:未知',
    purchase_date   date    default (now()) not null comment '购买日期',
    profile_picture varchar(1024)           not null comment '头像图片路径地址',
    status          tinyint                 not null comment '使用整数值来表示不同的枚举常量',
    kind            tinyint                 not null comment '使用整数值来表示不同的枚举常量',
    description     text                    null comment '描述',
    price           float   default 0.0     not null comment '购买金额',
    foreign key (user_id) references user (user_id),
    index idx_status (status asc)
);

drop table if exists recording;
create table recording
(
    tortoise_id  varchar(32)             not null,
    recording_id varchar(32) primary key not null comment 'UUID',
    dir          varchar(1024)           null comment '储存路径地址，目标是个文件夹',
    date         date                    not null comment '添加记录日期',
    description  text                    null comment '描述',
    size         float default 0.0       not null comment '背甲长度（cm）',
    weight       float default 0.0       not null comment '体重（g）',
    foreign key (tortoise_id) references tortoise (tortoise_id)
);

drop table if exists role;
create table role
(
    role_id     tinyint primary key auto_increment,
    name        varchar(16) not null unique,
    description text        not null comment '描述'
) auto_increment = 101;

drop table if exists user_role;
create table user_role
(
    user_role_id tinyint primary key auto_increment,
    user_id      varchar(32) not null,
    role_id      tinyint     not null,
    foreign key (user_id) references user (user_id),
    foreign key (role_id) references role (role_id)
);

drop table if exists permission;
create table permission
(
    permission_id tinyint primary key auto_increment,
    name          varchar(16) not null unique,
    description   text        not null comment '描述'
) auto_increment = 101;

drop table if exists role_permission;
create table role_permission
(
    role_permission_id tinyint primary key auto_increment,
    role_id            tinyint not null,
    permission_id      tinyint not null,
    foreign key (role_id) references role (role_id),
    foreign key (permission_id) references permission (permission_id)
);

drop table if exists kind;
create table kind
(
    kind_id tinyint primary key auto_increment,
    name    varchar(32)
) auto_increment = 1;

drop table if exists adoption;
create table adoption
(
    adoption_id varchar(32) primary key primary key comment '领养信息id',
    user_id     varchar(32)   not null comment '发布者id',
    content     text          not null comment '内容',
    create_date date          not null comment '发布日期',
    hot         int default 0 not null comment '热度',
    img         varchar(1024) not null comment '图片文件夹路径',
    foreign key (user_id) references user (user_id)
);

drop table if exists article;
create table article
(
    article_id varchar(32) primary key comment '文章id',
    title      varchar(32)   not null comment '标题',
    content    text          not null comment '内容',
    hot        int default 0 not null comment '热度',
    img        varchar(1024) not null comment '图片文件夹路径',
    data       date          not null comment '发布时间'
);

insert into user (user_id, username, password, profile_picture)
values ('no.1', 'admin', '{noop}root',
        'https://tortoise-record-book-img-storehouse.oss-cn-chengdu.aliyuncs.com/user/head-tilts/default-user-photo.png');

insert into kind (name)
values ('三线闭壳龟'),
       ('中华草龟'),
       ('黄喉拟水龟'),
       ('中华花龟'),
       ('鹰嘴龟'),
       ('蛇颈龟'),
       ('黄缘闭壳龟'),
       ('猪鼻龟');

insert into role (name, description)
values ('SUPER', '超级管理员用户'),
       ('USER_ADMIN', '用户管理员'),
       ('ARTICLE_ADMIN', '文章管理员'),
       ('ADOPTION_ADMIN', '领养信息管理员'),
       ('USER', '普通用户');