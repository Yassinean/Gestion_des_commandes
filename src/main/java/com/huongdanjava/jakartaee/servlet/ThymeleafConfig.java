package com.huongdanjava.jakartaee.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@WebListener
public class ThymeleafConfig implements ServletContextListener {
    public static final String TEMPLATE_ENGINE_ATTR = "com.huongdanjava.thymeleaf.TemplateEngineInstance";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialiser le Template Engine
        TemplateEngine templateEngine = new TemplateEngine();
        
        // Créer et configurer le resolver
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(sce.getServletContext());
        
        // Configuration de base
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        
        // Désactiver le cache pour le développement
        templateResolver.setCacheable(false);
        templateResolver.setCacheTTLMs(0L);
        
        // Configuration supplémentaire
        templateResolver.setCheckExistence(true);
        
        // Configurer le Template Engine
        templateEngine.setTemplateResolver(templateResolver);
        
        // Stocker dans le ServletContext
        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATTR, templateEngine);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nettoyage si nécessaire
    }
}