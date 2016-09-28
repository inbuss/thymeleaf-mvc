package hu.inbuss.thymeleaf.mvc;

import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class TemplateEngineProducer {
    private final Set<ITemplateResolver> resolvers;

    @Inject public TemplateEngineProducer(final TemplateResolvers resolvers) {
        this.resolvers = resolvers.getTemplateResolvers();
    }

    @Produces public TemplateEngine getTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolvers(resolvers);
        return engine;
    }
}
