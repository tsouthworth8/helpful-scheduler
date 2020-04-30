
BEGIN;

DROP TABLE IF EXISTS employee_requests;
DROP TABLE IF EXISTS time_off;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS users;

CREATE TABLE company (
        id serial PRIMARY KEY,
        name varchar(100) NOT NULL
);

CREATE TABLE users (
        id serial PRIMARY KEY,
        username varchar(255) NOT NULL UNIQUE,
        password varchar(32) NOT NULL, 
        salt varchar(256) NOT NULL,
        manager boolean NOT NULL,
        company_id int,
        
         CONSTRAINT fk_company_id FOREIGN KEY (company_id) REFERENCES company (id)
);

CREATE TABLE employee (
        id serial PRIMARY KEY,
        user_id int NOT NULL,
        first_name varchar(100) NOT NULL,
        last_name varchar(100) NOT NULL,
        
        CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE time_off (
        id serial PRIMARY KEY,
        date_requested date NOT NULL,
        date_off date NOT NULL,
        time_start int NOT NULL,
        time_end int NOT NULL
        
);
 
CREATE TABLE employee_requests (
        id serial PRIMARY KEY,
        employee_id int NOT NULL,
        time_off_id int NOT NULL,
        
        CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employee (id),
        CONSTRAINT fk_time_off_id FOREIGN KEY (time_off_id) REFERENCES time_off (id)
);

COMMIT;        
        
          
                
        
        
        
        
  
  
  