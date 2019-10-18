DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);

INSERT INTO user (username, password) VALUES
('user1', 'PASS1'),
('user2', 'password2');