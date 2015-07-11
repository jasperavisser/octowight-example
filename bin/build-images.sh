#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
set -e

# Build requisite images
bash ../octowight/bin/build-images.sh
pushd ../docker/
docker build --tag sample-data-repository sample-data-repository
popd
