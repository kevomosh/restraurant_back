#!/bin/bash
set -e




# Utility function to make it easy to use psql within the docker container
# This script sets up the default credentials that psql will use
# and exports the environment variables pointing to the db
#
# With this you should be able to run `psql` in the container and it will
# automatically log you into the db session
#
init_psql_env(){
    local params=$(echo "${DATASOURCE_URL#'jdbc:postgresql://'}:${DATASOURCE_USERNAME}:${DATASOURCE_PASSWORD}" | tr / :)
    {
        echo "$params"
    } > ~/.pgpass
    chmod 600 ~/.pgpass

    local vars=()
    IFS=':' read -ra vars <<< "$params"
    {
        echo "export PGHOST=\"${vars[0]}\""
        echo "export PGPORT=\"${vars[1]}\""
        echo "export PGDATABASE=\"${vars[2]}\""
        echo "export PGUSER=\"${vars[3]}\""
    } >> ~/psql.source

    echo "source ~/psql.source" > ~/.bashrc
    source ~/psql.source
}

[[ ! -f "${HOME}/psql.source" ]] && init_psql_env

unset vars
[[ ! -z "$@" ]] && exec "$@"
