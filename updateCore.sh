#!/bin/sh
set -e

jenkins_server=221.134.198.6
project=FrontlineSMS2-Core

build_url() {
    url=http://$1/job/$project/lastStableBuild/api/json?tree=actions[lastBuiltRevision[SHA1]]
    url=$(echo "$url" | sed -E 's/\[/%5B/g')
    url=$(echo "$url" | sed -E 's/\]/%5D/g')
    echo $url
}

if which curl >/dev/null
then
    get_command="curl --silent --connect-timeout 5"
elif which wget >/dev/null
then
    get_command="wget --quiet --tries=1 --connect-timeout=5"
else
    echo "Can't find curl or wget"
    exit 1
fi

url=$(build_url $jenkins_server) metadata=$($get_command "$url") ||
url=$(build_url localhost) metadata=$($get_command "$url")
last_successful_revision=$(echo "$metadata" | sed -E 's/.*"SHA1":"([0-9a-f]{40}).*/\1/')

cd frontlinesms2-core
git fetch
git checkout $last_successful_revision

echo "Updated core to revision $last_successful_revision"
