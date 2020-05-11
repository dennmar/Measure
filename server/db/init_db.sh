#!/bin/bash

set -e
set -u

create_user() {
    local adminname=$1
    local username=$2
    psql -v ON_ERROR_STOP=1 --username "$adminname" <<-EOSQL
	    CREATE USER $username;
EOSQL
}

create_db() {
    local adminname=$1
    local dbname=$2
    local username=$3
    psql -v ON_ERROR_STOP=1 --username "$adminname" <<-EOSQL
	    CREATE DATABASE $dbname;
	    GRANT ALL PRIVILEGES ON DATABASE $dbname TO $username;
EOSQL
}

main() {
    create_user ${POSTGRES_USER} ${POSTGRES_USER}
    create_db ${POSTGRES_USER} ${PG_DB} ${POSTGRES_USER}
    create_db ${POSTGRES_USER} ${PG_TEST_DB} ${POSTGRES_USER}
}

main