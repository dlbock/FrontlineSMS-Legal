#!/bin/sh
set -e

jenkins_server=221.134.198.6
project=FrontlineSMS2-Core

metadata_url=http://$jenkins_server/job/$project/lastStableBuild/api/json?tree=actions[lastBuiltRevision[SHA1]]
metadata_url=${metadata_url//[/%5B}
metadata_url=${metadata_url//]/%5D}

metadata=$(curl --silent "$metadata_url")
last_successful_revision=$(echo "$metadata" | sed -E 's/.*"SHA1":"([0-9a-f]{40}).*/\1/')

cd frontlinesms2-core
git fetch
git checkout $last_successful_revision

echo "Updated core to revision $last_successful_revision"
