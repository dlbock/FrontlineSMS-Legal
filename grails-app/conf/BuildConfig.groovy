grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.plugin.location.frontlinesms2 = "frontlinesms2-core"

grails.project.dependency.resolution = {
    inherits("global") {
    }

    log "warn"

    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        compile 'org.mortbay.jetty:jetty:6.1.14'
        compile 'org.mortbay.jetty:jetty-util:6.1.14'

        test "org.codehaus.geb:geb-core:0.6.0"
        test "org.codehaus.geb:geb-spock:0.6.0"
        test "org.seleniumhq.selenium:selenium-firefox-driver:2.4.0"
        test "org.spockframework:spock-core:0.5-groovy-1.7"
    }
}

coverage {
    xml = true
    enabledByDefault = false
}
