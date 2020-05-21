
BEGIN;

DROP TABLE IF EXISTS day_week_templates;
DROP TABLE IF EXISTS week_templates;
DROP TABLE IF EXISTS shift_day_templates;
DROP TABLE IF EXISTS day_templates;
DROP TABLE IF EXISTS roles_templates;
DROP TABLE IF EXISTS shift_templates;
DROP TABLE IF EXISTS shift_templates_company;
DROP TABLE IF EXISTS shift_templates_employee;
DROP TABLE IF EXISTS shift_roles;
DROP TABLE IF EXISTS employee_requests;
DROP TABLE IF EXISTS time_off;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS company;


CREATE TABLE company (
        id serial PRIMARY KEY,
        name varchar(100) NOT NULL
);

CREATE TABLE users (
        id serial PRIMARY KEY,
        username varchar(255) NOT NULL UNIQUE,
        email varchar(255) NOT NULL UNIQUE,
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

CREATE TABLE shift_roles (
        id serial PRIMARY KEY,
        company_id int NOT NULL,
        title varchar(100) NOT NULL,
        
        CONSTRAINT fk_shiftrole_company FOREIGN KEY (company_id) REFERENCES company(id)
);

--TEMPLATE TABLES:
CREATE TABLE shift_templates (
        id serial PRIMARY KEY,
        start_time time NOT NULL,
        end_time time NOT NULL
);

CREATE TABLE shift_templates_company (
        shift_template_id int NOT NULL,
        company_id int NOT NULL,

        CONSTRAINT pk_shift_templates_company PRIMARY KEY (shift_template_id, company_id),
        CONSTRAINT fk_shift_templates_company_templates FOREIGN KEY (shift_template_id) REFERENCES shift_templates(id),
        CONSTRAINT fk_shift_templates_company_company FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE shift_templates_employee (
        shift_template_id int NOT NULL,
        employee_id int NOT NULL,

        CONSTRAINT pk_shift_templates_employee PRIMARY KEY (shift_template_id, employee_id),
        CONSTRAINT fk_shift_templates_employee_templates FOREIGN KEY (shift_template_id) REFERENCES shift_templates(id),
        CONSTRAINT fk_shift_templates_employee_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE roles_templates (
        shift_role_id int NOT NULL,
        shift_template_id int NOT NULL,

        CONSTRAINT pk_roles_templates PRIMARY KEY (shift_role_id, shift_template_id),
        CONSTRAINT fk_roles_templates_role FOREIGN KEY (shift_role_id) REFERENCES shift_roles(id),
        CONSTRAINT fk_roles_templates_templates FOREIGN KEY (shift_template_id) REFERENCES shift_templates(id)
);

CREATE TABLE day_templates (
        id serial PRIMARY KEY,
        nickname varchar(250) NOT NULL
);

CREATE TABLE shift_day_templates (
        shift_id int NOT NULL,
        day_id int NOT NULL,

        CONSTRAINT pk_shift_day PRIMARY KEY (shift_id, day_id),
        CONSTRAINT fk_shift_day_shift FOREIGN KEY (shift_id) REFERENCES shift_templates(id),
        CONSTRAINT fk_shift_day_day FOREIGN KEY (day_id) REFERENCES day_templates(id)
);

CREATE TABLE week_templates (
        id serial PRIMARY KEY,
        nickname varchar(250) NOT NULL
);

CREATE TABLE day_week_templates (
        day_id int NOT NULL,
        week_id int NOT NULL,

        CONSTRAINT pk_day_week PRIMARY KEY (day_id, week_id),
        CONSTRAINT fk_day_week_day FOREIGN KEY (day_id) REFERENCES day_templates(id),
        CONSTRAINT fk_day_week_week FOREIGN KEY (week_id) REFERENCES week_templates(id)
);

COMMIT;        
        
          
                
        
        
        
        
  
  
  