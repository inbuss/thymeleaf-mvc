package hu.inbuss.thymeleaf.mvc;

import java.io.IOException;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;

/**
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class ViewEngine implements javax.mvc.engine.ViewEngine {
    private final TemplateEngine engine;
    private final MVCWebContextFactory ctxFactory;
    private final TemplateResolvers resolvers;

    @Inject public ViewEngine(final TemplateEngine engine, final MVCWebContextFactory ctxFactory,
                              final TemplateResolvers resolvers) {
        this.engine = engine;
        this.ctxFactory = ctxFactory;
        this.resolvers = resolvers;
    }

    @Override public boolean supports(final String view) {
        return resolvers.supports(view);
    }

    @Override public void processView(final ViewEngineContext context) throws ViewEngineException {
        try {
            final IWebContext ctx = ctxFactory.create(context);
            engine.process(context.getView(), ctx, context.getResponse().getWriter());
        } catch (IOException e) {
            throw new ViewEngineException(e);
        }
    }
}
