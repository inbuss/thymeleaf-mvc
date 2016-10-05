package hu.inbuss.thymeleaf.mvc;

import java.io.IOException;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;

/**
 * A bean providing an MVC {@link ViewEngine} by wrapping a Thymeleaf {@link TemplateEngine}. The template engine is
 * injected as a bean, and is (by default) constructed by {@link TemplateEngineProducer#getTemplateEngine()}.
 * Customizations should be done by specializing that producer method.
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class ViewEngine implements javax.mvc.engine.ViewEngine {
    private final TemplateEngine engine;
    private final MVCWebContextFactory ctxFactory;

    @Inject protected ViewEngine(final TemplateEngine engine, final MVCWebContextFactory ctxFactory) {
        this.engine = engine;
        this.ctxFactory = ctxFactory;
    }

    @Override public boolean supports(final String view) {
//        for (final ITemplateResolver tr : engine.getTemplateResolvers())
//            if (tr.supports(view)) <-- need a cheap way to do this
//                return true;
//        return false;
        return view.endsWith(".html");
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
