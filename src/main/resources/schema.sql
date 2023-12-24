CREATE TABLE threads
(
   id INT NOT NULL AUTO_INCREMENT,
   user_id INT NOT NULL,
   name VARCHAR(100) NOT NULL,
   explain VARCHAR(500) NOT NULL,
   created_at DATETIME NOT NULL,
   updated_at DATETIME NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE posts
(
   id INT NOT NULL AUTO_INCREMENT,
   thread_id INT NOT NULL,
   user_id INT NOT NULL,
   title VARCHAR(100) NOT NULL,
   description VARCHAR(500) NOT NULL,
   created_at DATETIME NOT NULL,
   updated_at DATETIME NOT NULL,
   PRIMARY KEY(id)
);