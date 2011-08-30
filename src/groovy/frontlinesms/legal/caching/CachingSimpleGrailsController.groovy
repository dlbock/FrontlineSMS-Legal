package frontlinesms.legal.caching

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.codehaus.groovy.grails.web.servlet.mvc.SimpleGrailsController
import org.springframework.web.servlet.ModelAndView

public class CachingSimpleGrailsController extends SimpleGrailsController {
    private static final String HEADER_EXPIRES = "Expires";
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
        response.addHeader(HEADER_CACHE_CONTROL, "no-store");
        response.setDateHeader(HEADER_EXPIRES, 1L);
        return super.handleRequest(request, response)
    }
}
