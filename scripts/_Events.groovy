eventCleanEnd = {
    ant.delete(dir: "web-app/js")
    ant.delete(dir: "web-app/css")
}

eventCompileEnd = { compilationType ->
    ant.java(jar: "lib/java/jruby-complete-1.6.2.jar", fork: "true") {
        def command = "-rlib/ruby/sass.jar "
        command += "-e \"require 'rubygems';load Gem.bin_path('sass', 'sass', '3.1.3')\" "
        command += "-- --update src/sass:web-app/css"
        arg(line: command)
    }
    ant.copy(todir: "web-app/js/", verbose: "true") {
        fileset(dir: "src/javascript/", includes: "*.js")
        fileset(dir: "lib/javascript/", includes: "*.js")
    }
}

unJarTask = {jarName, includePattern ->
    ant.mkdir(dir: "$stagingDir/tmp/")
    ant.unjar(src: "$stagingDir/WEB-INF/lib/$jarName", dest: "$stagingDir/tmp")
    ant.move(todir: "$stagingDir") {
        fileset(dir: "$stagingDir/tmp") {
            include(name: "$includePattern/**")
            exclude(name: "META-INF/**")
        }
    }
    ant.delete(dir: "$stagingDir/tmp")
}

eventCreateWarStart = {warName, stagingDir ->
    ant.mkdir(dir: "$stagingDir/frontlinesms/legal/")
    ant.move(file: "$stagingDir/WEB-INF/classes/frontlinesms/legal/JettyWebappRunner.class", todir: "$stagingDir/frontlinesms/legal/")
    unJarTask("jetty-6.1.14.jar", "org")
    unJarTask("jetty-util-6.1.14.jar", "org")
    unJarTask("servlet-api-2.5-6.1.14.jar", "javax")
    ant.delete(file: "$stagingDir/WEB-INF/lib/jmxri-1.2.1.jar")
}

eventCreateWarEnd = {warName, stagingDir ->
    def libPath = ""
    File f = new File("$stagingDir/WEB-INF/lib")
    if (f.exists()) {
        f.eachFile { libPath += "WEB-INF/lib/${it.name} " }
    }
    ant.jar(destfile: warName, update: true) {
        manifest { attribute(name: "Main-Class", value: "frontlinesms.legal.JettyWebappRunner") }
        manifest { attribute(name: "Class-Path", value: "$libPath") }
    }
}