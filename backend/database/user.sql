-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER manager WITH PASSWORD 'password1';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO manager;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO manager;

CREATE USER employee WITH PASSWORD 'password1';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO employee;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO employee;
