DROP DATABASE IF EXISTS blog_db;
CREATE DATABASE blog_db;

use blog_db;

DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;
drop table if exists roles;
drop table if exists user_role;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS post_tag;

CREATE TABLE users (
    id       INT UNSIGNED        NOT NULL AUTO_INCREMENT,
    username VARCHAR(240) UNIQUE NOT NULL,
    email    VARCHAR(240) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

-- create table roles (
--     id int unsigned not null auto_increment,
--     name varchar(250) not null,
--     PRIMARY key (id)
-- );

CREATE TABLE user_role (
    id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    role varchar(255) NOT NULL,
    user_id int unsigned not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE posts (
    id      INT UNSIGNED  NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED  NOT NULL,
    title   VARCHAR(240)  NOT NULL,
    body    TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE tags (
    id     INT UNSIGNED        NOT NULL AUTO_INCREMENT,
    name   VARCHAR(240) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post_tag (
    post_id INT UNSIGNED NOT NUlL,
    tag_id  INT UNSIGNED NOT NUlL,
    PRIMARY KEY(post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts(id)
        ON DELETE CASCADE,
    FOREIGN KEY (tag_id)  REFERENCES tags(id)
        ON DELETE CASCADE
);
