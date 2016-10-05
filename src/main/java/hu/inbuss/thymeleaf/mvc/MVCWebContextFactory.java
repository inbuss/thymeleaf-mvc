package hu.inbuss.thymeleaf.mvc;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.mvc.engine.ViewEngineContext;
import javax.servlet.ServletContext;
import org.thymeleaf.context.IWebContext;

/**
 * Factory bean for creating {@link MVCWebContext} instances.
 *
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class MVCWebContextFactory {
    private final BeanManager beanManager;
    private final ServletContext servletContext;

    @Inject public MVCWebContextFactory(final BeanManager beanManager, final ServletContext servletContext) {
        this.beanManager = beanManager;
        this.servletContext = servletContext;
    }

    /**
     * Factory method for creating an {@link MVCWebContext} instance.
     *
     * @param ctx the {@link ViewEngineContext} instance describing the context of the current rendering operation
     * @return the newly created {@link MVCWebContext} instance
     */
    public IWebContext create(final ViewEngineContext ctx) {
        return new MVCWebContext(ctx.getRequest(), ctx.getResponse(), beanManager, servletContext, ctx.getModels());
    }
}
