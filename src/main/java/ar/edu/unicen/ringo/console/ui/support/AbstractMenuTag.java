package ar.edu.unicen.ringo.console.ui.support;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * Base class for menu tags, does some initial cleaning.
 * @author psaavedra
 */
public abstract class AbstractMenuTag extends HtmlEscapingAwareTag {

    /**
     * 
     */
    private static final long serialVersionUID = 2679280630905940881L;

    @Override
    protected int doStartTagInternal() throws Exception {
        Menu menu = getRequestContext().getWebApplicationContext().getBean(
                Menu.class);
        String uri = getRequestContext().getRequestUri().substring(
                getRequestContext().getContextPath().length());
        TagWriter out = new TagWriter(this.pageContext);
        doWriteMenu(menu, out, uri);
        return SKIP_BODY;
    }

    protected abstract void doWriteMenu(Menu menu, TagWriter out, String uri) throws JspException;

}
