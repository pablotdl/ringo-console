package ar.edu.unicen.ringo.console.ui.support;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * A JSP tag that renders a menu.
 * @author psaavedra
 */
public class MenuTag extends AbstractMenuTag {

    /**
     * 
     */
    private static final long serialVersionUID = -7469984647662825322L;

    @Override
    protected void doWriteMenu(Menu menu, TagWriter out, String uri)
            throws JspException {
        out.startTag("ul");
        out.writeAttribute("id", menu.getId());
        out.writeAttribute("class", menu.getStyleClass());
        out.forceBlock();
        for (Item it : menu.getItems()) {
            out.startTag("li");
            if (it.isActive(uri)) {
                out.writeAttribute("class","active"); 
            }
            out.forceBlock();
            out.startTag("a"); out.writeAttribute("href", "#" + it.getName());
            out.writeAttribute("data-toggle", "tab");
            out.forceBlock();
            out.startTag("i"); out.writeAttribute("class", it.getStyleClass());
            out.endTag(true);
            out.appendValue(" " + it.getLabel());
            out.endTag();
            out.endTag();
        }
        out.endTag();
        
    }

}
