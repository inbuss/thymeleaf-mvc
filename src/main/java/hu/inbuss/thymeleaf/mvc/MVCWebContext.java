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
        final Set<String> res = new HashSet<String>(models.keySet());
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
