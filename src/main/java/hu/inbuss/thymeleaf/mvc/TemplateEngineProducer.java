package hu.inbuss.thymeleaf.mvc;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class TemplateEngineProducer {
    @Inject private ServletContext servletContext;

    @Produces public TemplateEngine getTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        ITemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
        engine.setTemplateResolver(resolver);
        return engine;
    }
}
