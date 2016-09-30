package hu.inbuss.thymeleaf.mvc;

import hu.inbuss.thymeleaf.jaxrs.ParamConverterBridge;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import org.thymeleaf.TemplateEngine;

/**
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class TemplateEngineProducer {
    @Inject private TemplateResolvers resolvers;
    @Inject private BeanManager beanManager;

    @Produces public TemplateEngine getTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolvers(resolvers.getTemplateResolvers());
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
