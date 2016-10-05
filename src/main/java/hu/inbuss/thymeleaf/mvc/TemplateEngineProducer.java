package hu.inbuss.thymeleaf.mvc;

import hu.inbuss.thymeleaf.jaxrs.ParamConverterBridge;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.mvc.MvcContext;
import javax.servlet.ServletContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * A bean building and configuring the {@link TemplateEngine} instance to be used in the application. Subclasses can be
 * used to specialize the producer method and extend or override the default configuration.
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class TemplateEngineProducer {
    @Inject private BeanManager beanManager;
    @Inject private MvcContext mvcContext;
    @Inject private ServletContext servletContext;

    /**
     * Producer method building and configuring the {@link TemplateEngine} instance to be used in the application. The
     * engine's template resolver is set up to follow MVC requirements. JAX-RS parameter conversion bridges are
     * detected, and if one is present, the engine is set up to use it.
     *
     * Subclasses can override and {@link javax.enterprise.inject.Specializes specialize} this method to add additional
     * configuration (e.g. call {@link TemplateEngine#addDialect(org.thymeleaf.dialect.IDialect)} to add a dialect).
     *
     * @return the newly created and configured {@link TemplateEngine} instance
     */
    @Produces public TemplateEngine getTemplateEngine() {
        final TemplateEngine engine = new TemplateEngine();
        final ITemplateResolver tr = new MVCTemplateResolver(servletContext, mvcContext);
        engine.setTemplateResolver(tr);
        try {
            final Class<?> pcbClass = Class.forName("hu.inbuss.thymeleaf.jaxrs.ParamConverterBridge");
            final Bean<?> pcbBean = beanManager.resolve(beanManager.getBeans(pcbClass));
            final CreationalContext pcbContext = beanManager.createCreationalContext(pcbBean);
            final ParamConverterBridge pcb = (ParamConverterBridge) beanManager.getReference(pcbBean, pcbClass, pcbContext);
            pcb.installInto(engine);
        } catch (final ClassNotFoundException cnfe) {
            // the Jersey integration module is not present, do nothing
        }
        return engine;
    }
}
