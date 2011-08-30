includeTargets << grailsScript("_GrailsProxy")
includeTargets << grailsScript("_GrailsPlugins")

target(updateCore: "Update FrontlineSMS Core plugin") {
    def hostNames = ["221.134.198.6", "localhost"]
    def urlsToTry = hostNames.collect {"http://${it}/job/FrontlineSMS2-Core/lastSuccessfulBuild/artifact/grails-frontlinesms2-0.1-SNAPSHOT.zip"}
    def url = urlsToTry.find {connectionAvailable(new URL(it))}
    if (url) {
        args = url
        installPlugin()
    } else {
        echo "WARNING: Could not load the latest version of FrontlineSMS Core"
    }
}

private def connectionAvailable(url) {
    try {
        url.openStream()
        true
    } catch (exception) {
        false
    }
}

setDefaultTarget(updateCore)
