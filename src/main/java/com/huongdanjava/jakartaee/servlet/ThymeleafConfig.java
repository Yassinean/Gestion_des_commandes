package com.huongdanjava.jakartaee.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@WebListener
public class ThymeleafConfig implements ServletContextListener {

    public static final String TEMPLATE_ENGINE_ATTR = "com.huongdanjava.thymeleaf.TemplateEngineInstance";

    private ITemplateEngine templateEngine;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the Thymeleaf template engine
        this.templateEngine = createTemplateEngine(sce.getServletContext());

        // Store the template engine in the ServletContext for easy access
        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATTR, this.templateEngine);
    }

    private ITemplateEngine createTemplateEngine(javax.servlet.ServletContext servletContext) {
        TemplateEngine templateEngine = new TemplateEngine();

        // Create and configure the template resolver
        ServletContextTemplateResolver templateResolver = createTemplateResolver(servletContext);
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private ServletContextTemplateResolver createTemplateResolver(javax.servlet.ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        // Set the template mode to HTML
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Define the directory where the templates are stored
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // Enable template caching and set cache TTL to 1 hour
        templateResolver.setCacheTTLMs(3600000L);
        // Set to true to cache templates, or false to reload templates every time they are accessed
        templateResolver.setCacheable(true);

        return templateResolver;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No operation needed
    }
}
