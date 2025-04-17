
CREATE TABLE viewer (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    ticketid INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    lastlogin DATE NOT NULL,
    status BIT NOT NULL
);
