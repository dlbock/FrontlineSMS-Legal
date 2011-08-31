#!/bin/sh
set -e

if [ -d $GRAILS_HOME ]
then
    echo "Using Grails in $GRAILS_HOME"
    before="$(date +%s)"
    grails clean
    grails update-core
    grails codenarc
    grails sass-compile
    grails test-app -coverage
    grails dist
    after="$(date +%s)"
    elapsed_time="$(expr $after - $before)"
    echo "Build took $elapsed_time secs."
else
    echo "GRAILS_HOME is not set."
fi