package hu.inbuss.thymeleaf.mvc;

import hu.inbuss.thymeleaf.cdi.CDIWebContext;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.inject.spi.BeanManager;
import javax.mvc.Models;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A Thymeleaf web context providing MVC models as expression roots. It extends base CDI functionality by adding model
 * objects registered via the MVC {@link Models} facility to the available set of CDI named beans.
 *
 * Explicitly registered model objects are prioritized over named beans. According to the MVC specification,
 * "application developers are encouraged to use CDI-based models whenever supported", so the {@link Models} way should
 * not be used in applications using this extension.
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
public class MVCWebContext extends CDIWebContext {
    private final Models models;

    protected MVCWebContext(final HttpServletRequest request, final HttpServletResponse response,
                            final BeanManager beanManager, final ServletContext servletContext, final Models models) {
        super(request, response, beanManager, servletContext);
        this.models = models;
    }

    @Override public Set<String> getVariableNames() {
        final Set<String> res = new HashSet<>(models.keySet());
        res.addAll(super.getVariableNames());
        return res;
    }

    @Override public boolean containsVariable(final String name) {
        return models.containsKey(name) || super.containsVariable(name);
    }

    @Override public Object getVariable(final String name) {
        if (models.containsKey(name))
            return models.get(name);
        return super.getVariable(name);
    }
}
