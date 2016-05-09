USE redditviewer;

CREATE TABLE user (
userid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(4000) NOT NULL,
password VARCHAR(4000) NOT NULL
);

CREATE TABLE  link (
linkid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
userName VARCHAR(4000) NOT NULL REFERENCES user(name),
url VARCHAR(4000) NOT NULL,
title VARCHAR(4000) NOT NULL,
domain VARCHAR(4000) NOT NULL,
author VARCHAR(4000) NOT NULL,
subreddit VARCHAR(4000) NOT NULL,
num_comments INT NOT NULL,
permalink VARCHAR(4000) NOT NULL
);

INSERT INTO user (name, password) VALUES ('test', 'test');
INSERT INTO link (userName, url, title, domain, author, subreddit, num_comments, permalink) VALUES ('user', 'dank memez', 'hi daniel', 'hi daniel', 'hi daniel', 'di haniel', 5, 'yo');
