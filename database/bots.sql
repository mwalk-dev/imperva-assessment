GRANT ALL PRIVILEGES ON optic_data.* TO 'spring'@'%' IDENTIFIED BY 'spring';
GRANT ALL PRIVILEGES ON optic_data.* TO 'spring'@'localhost' IDENTIFIED BY 'spring';
USE optic_data;
CREATE TABLE bots(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE attacks(
    id INT NOT NULL AUTO_INCREMENT,
    site VARCHAR(100),
    attack_date DATE,
    PRIMARY KEY (id)
);
CREATE TABLE attack_bots(
    bot_id INT,
    attack_id INT,
    FOREIGN KEY (bot_id) REFERENCES bots(id),
    FOREIGN KEY (attack_id) REFERENCES attacks(id)
);