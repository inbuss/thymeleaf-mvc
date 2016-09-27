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

    @Inject public ViewEngine(final TemplateEngine engine, final MVCWebContextFactory ctxFactory) {
        this.engine = engine;
        this.ctxFactory = ctxFactory;
    }

    @Override
    public boolean supports(final String view) {
        return view.endsWith(".html"); // TODO
    }

    private String resolveView(final ViewEngineContext context) {
        final String view = context.getView();
        if (view.charAt(0) == '/') {
            return view;
        } else {
            final Object propval = context.getConfiguration().getProperty(VIEW_FOLDER);
            final String viewFolder = propval instanceof String ? (String) propval : DEFAULT_VIEW_FOLDER;
            final StringBuilder sb = new StringBuilder(viewFolder);
            if (sb.charAt(sb.length() - 1) != '/')
                sb.append('/');
            return sb.append(view).toString();
        }
    }

    @Override
    public void processView(final ViewEngineContext context) throws ViewEngineException {
        try {
            final IWebContext ctx = ctxFactory.create(context);
            engine.process(resolveView(context), ctx, context.getResponse().getWriter());
        } catch (IOException e) {
            throw new ViewEngineException(e);
        }
    }
}
