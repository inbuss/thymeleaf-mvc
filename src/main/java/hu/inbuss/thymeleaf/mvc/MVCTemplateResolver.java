package hu.inbuss.thymeleaf.mvc;

import java.util.Map;
import javax.mvc.MvcContext;
import javax.servlet.ServletContext;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Template resolver implementing the requirements of the MVC standard. It looks for the templates in the application
 * archive. Relative names are resolved relative to {@code /WEB-INF/views} by default; this can be overridden using the
 * JAX-WS configuration property {@code ViewEngine.viewFolder}.
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
public class MVCTemplateResolver extends ServletContextTemplateResolver {
    private final MvcContext mvcContext;

    /**
     * Create a new template resolver instance.
     * @param servletContext the servlet context for the application
     * @param mvcContext the MVC context for the application
     */
    public MVCTemplateResolver(final ServletContext servletContext, final MvcContext mvcContext) {
        super(servletContext);
        this.mvcContext = mvcContext;
    }

    @Override protected String computeResourceName(final IEngineConfiguration configuration, final String owner,
                                                   final String template, final String prefix, final String suffix,
                                                   final Map<String, String> aliases, final Map<String, Object> trAttrs) {
        final String view = super.computeResourceName(configuration, owner, template, prefix, suffix, aliases, trAttrs);
        if (view.charAt(0) == '/') {
            return view;
        } else {
            final Object propval = mvcContext.getConfig().getProperty(ViewEngine.VIEW_FOLDER);
            final String viewFolder = propval instanceof String ? (String) propval : ViewEngine.DEFAULT_VIEW_FOLDER;
            final StringBuilder sb = new StringBuilder(viewFolder);
            if (sb.charAt(sb.length() - 1) != '/')
                sb.append('/');
            return sb.append(view).toString();
        }
    }
}
