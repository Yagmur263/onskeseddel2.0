
CREATE TABLE IF NOT EXISTS onskeliste (
id INT AUTO_INCREMENT PRIMARY KEY,
titel VARCHAR(100) NOT NULL,
delingskode VARCHAR(36)  NOT NULL UNIQUE
 );


CREATE TABLE IF NOT EXISTS onske (
    id INT AUTO_INCREMENT PRIMARY KEY,
onskeliste_id INT NOT NULL,
navn VARCHAR(100)  NOT NULL,
    beskrivelse   VARCHAR(500),
    link          VARCHAR(500),
    pris          DECIMAL(10,2) DEFAULT 0,
    antal         INT           NOT NULL DEFAULT 1,
    koebt         BOOLEAN       NOT NULL DEFAULT FALSE,

FOREIGN KEY (onskeliste_id) REFERENCES onskeliste(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS person (
 navn VARCHAR(100) NOT NULL
    );