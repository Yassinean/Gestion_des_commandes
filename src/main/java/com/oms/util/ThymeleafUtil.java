package com.oms.util;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.oms.model.Product;

public class ThymeleafUtil {

    private final Logger logger = LoggerFactory.getLogger(ThymeleafUtil.class);
    private final TemplateEngine templateEngine;

    public ThymeleafUtil(ServletContext context) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        // Initialize the template engine
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    public void returnView(HttpServletRequest req, HttpServletResponse resp, String view, Product model) {
        // Create the Thymeleaf context
        WebContext ctx = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        ctx.setVariable("model", model);
        
        try {
            templateEngine.process(view, ctx, resp.getWriter());
        } catch (IOException e) {
            logger.error("Error processing Thymeleaf template: {}", e.getMessage(), e);
        }
    }
}
