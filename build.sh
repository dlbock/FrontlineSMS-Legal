#!/bin/sh
set -e

if [ -z "$GRAILS_HOME" ]
then
    echo "Warning: GRAILS_HOME is not set; proceeding anyway"
elif [ -d "$GRAILS_HOME" ]
then
    echo "Using Grails in $GRAILS_HOME"
else
    echo "Error: GRAILS_HOME is set to $GRAILS_HOME but that is not a directory"
    exit 1
fi

before="$(date +%s)"
grails clean --non-interactive
if [ "$*" != "--no-update" ]
then
    ./updateCore.sh
fi
grails sass-compile --non-interactive
grails -Dserver.port=4000 test-app -coverage --non-interactive
grails war --non-interactive
after="$(date +%s)"
elapsed_time="$(expr $after - $before)"
echo "Build took $elapsed_time secs."
