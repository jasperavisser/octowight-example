#!/bin/bash

COMMAND="$1"
docker exec -it octowightexample_sampleDataRepository_1 psql -U postgres postgres -c "${COMMAND}"
