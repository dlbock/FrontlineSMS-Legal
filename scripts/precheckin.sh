#!/bin/sh
set -e

if [ -d $GRAILS_HOME ]
then
    echo "Using Grails in $GRAILS_HOME"
    grails clean
    grails update-core
    grails codenarc
    grails sass-compile
    grails test-app -coverage
    grails dist
else
    echo "GRAILS_HOME is not set."
fi