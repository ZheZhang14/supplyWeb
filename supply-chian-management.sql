create table user
(
    id           int auto_increment
        primary key,
    username     varchar(255)                                              not null,
    password     varchar(255)                                              not null,
    email        varchar(255)                                              not null,
    contact_name varchar(255)                                              null,
    phone        varchar(255)                                              null,
    user_role    enum ('admin', 'supplier', 'distributor', 'manufacturer') not null,
    date_created timestamp default CURRENT_TIMESTAMP                       null,
    address      varchar(255)                                              null,
    image_path   varchar(255)                                              null
);

create table product
(
    id           int auto_increment
        primary key,
    product_name varchar(255)                                         not null,
    description  varchar(255)                                         null,
    price        decimal(10, 2)                                       not null,
    user_id      int                                                  not null,
    stage        enum ('On_Market', 'Off_Market') default 'On_Market' not null,
    constraint product_ibfk_1
        foreign key (user_id) references user (id)
);

create table inventory
(
    id                 int auto_increment
        primary key,
    product_id         int                         null,
    stock              int          default 0      null,
    damagedGoods_count int          default 0      null,
    valuation          varchar(255) default 'None' null,
    expiredGoods_count int          default 0      null,
    constraint inventory_ibfk_1
        foreign key (product_id) references product (id)
);

create index ProductID
    on inventory (product_id);

create table `order`
(
    id           int auto_increment
        primary key,
    user_id      int                                                                             null,
    total_amount decimal(10, 2)                                                                  not null,
    product_id   int                                                                             null,
    count        int                                                                             not null,
    order_type   enum ('Create', 'Return')                                     default 'Create'  not null,
    status       enum ('Created', 'In_Progress', 'Return', 'Rejected', 'Done') default 'Created' not null,
    constraint order_ibfk_1
        foreign key (user_id) references user (id),
    constraint order_ibfk_2
        foreign key (product_id) references product (id)
);

create index UserID
    on `order` (user_id);

create index product_id
    on `order` (product_id);

create index userId
    on product (user_id);


