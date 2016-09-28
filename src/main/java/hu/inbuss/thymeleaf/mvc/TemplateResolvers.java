package hu.inbuss.thymeleaf.mvc;

import java.util.Collections;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mvc.MvcContext;
import javax.servlet.ServletContext;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author PÁLFALVI Tamás &lt;tamas.palfalvi@inbuss.hu&gt;
 */
@Dependent
public class TemplateResolvers {
    protected final MVCTemplateResolver mvcResolver;

    @Inject public TemplateResolvers(final ServletContext servletContext, final MvcContext mvcContext) {
        this.mvcResolver = new MVCTemplateResolver(servletContext, mvcContext);
    }

    public Set<ITemplateResolver> getTemplateResolvers() {
        return Collections.singleton(mvcResolver);
    }

    public boolean supports(final String view) {
        // dummy for now - should only say true if the resource is actually resolvable (in order to co-exist
        // with other view engines), but related Thymeleaf functionality is not accessible from here
        return view.endsWith(".html");
    }
}
