import frontlinesms.legal.caching.CachingSimpleGrailsController

// Place your Spring DSL code here
beans = {
    mainSimpleController(CachingSimpleGrailsController) {
        grailsApplication = ref('grailsApplication', true)
    }
}
