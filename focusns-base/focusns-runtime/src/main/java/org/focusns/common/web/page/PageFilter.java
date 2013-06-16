package org.focusns.common.web.page;

import org.focusns.common.web.WebUtils;
import org.focusns.common.web.page.config.PageConfig;
import org.focusns.common.web.page.config.PageFactory;
import org.focusns.common.web.page.engine.PageEngine;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageFilter extends OncePerRequestFilter {
    private static final String DEFAULT_LAYOUT_LOCATION = "/WEB-INF/themes/default/layout.jsp";
    private static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/spring/pageContext.xml";
    //
    private WebApplicationContext pageContext;
    private PageFactory pageFactory;
    private PageEngine pageEngine;
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    //
    private String defaultLayout = DEFAULT_LAYOUT_LOCATION;
    private String configLocation = DEFAULT_CONFIG_LOCATION;

    public void setDefaultLayout(String defaultLayout) {
        this.defaultLayout = defaultLayout;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public WebApplicationContext getPageContext() {
        //
        WebApplicationContext parent = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        //
        if(pageContext==null) {
            XmlWebApplicationContext xmlPageContext = new XmlWebApplicationContext();
            xmlPageContext.setId("pageContext");
            xmlPageContext.setParent(parent);
            xmlPageContext.setServletContext(getServletContext());
            xmlPageContext.setConfigLocation(configLocation);
            xmlPageContext.refresh();
            pageContext = xmlPageContext;
        }
        //
        return pageContext;
    }

    @Override
    protected void initFilterBean() throws ServletException {
        this.pageFactory = getPageContext().getBean(PageFactory.class);
        this.pageEngine = getPageContext().getBean(PageEngine.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //
        String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
        if(lookupPath.equals("/") || lookupPath.equals("")) {
            lookupPath = "/index";
        }
        //
        PageConfig pageConfig = pageFactory.find(lookupPath, WebUtils.getParameterMap(request));
        if(pageConfig!=null) {
            request.setAttribute("pageConfig", pageConfig);
            request.getSession().setAttribute("pageConfig", pageConfig);
            pageEngine.doRender(request, response);
            //
            String layout = !StringUtils.hasText(pageConfig.getLayout()) ? defaultLayout : pageConfig.getLayout();
            request.getRequestDispatcher(layout).forward(request, response);
            //
            return;
        }
        //
        String widgetId = request.getParameter("widgetId");
        if(widgetId!=null) {
            request.setAttribute("widgetId", widgetId);
            pageEngine.doAction(request, response);
            //
            return ;
        }
        //
        filterChain.doFilter(request, response);
    }

}
