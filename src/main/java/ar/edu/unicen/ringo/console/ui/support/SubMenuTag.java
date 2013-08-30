package ar.edu.unicen.ringo.console.ui.support;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * A JSP tag that renders the submenu.
 * @author psaavedra
 */
public class SubMenuTag extends AbstractMenuTag {

    /**
     * 
     */
    private static final long serialVersionUID = -1237584473687428484L;

    @Override
    protected void doWriteMenu(Menu menu, TagWriter out, String uri)
            throws JspException {
        out.startTag("div");
        out.writeAttribute("class", "tab-content");
        out.forceBlock();
        for (Item topLevel : menu.getItems()) {
            out.startTag("div");
            out.writeAttribute("id", topLevel.getName());
            String paneClass = "tab-pane";
            if (topLevel.isActive(uri)) {
                paneClass += " active";
            }
            out.writeAttribute("class", paneClass);
            out.startTag("ul");
            out.writeAttribute("class", "additional-menu");
            out.writeAttribute("style", "margin-left:0px");
            for (Item it : topLevel.getItem()) {
                out.startTag("li");
                if (it.isActive(uri)) {
                    out.writeAttribute("class", "active");
                }
                out.startTag("a");
                out.writeAttribute("href", getRequestContext().getContextUrl(it.getHref()));
                out.startTag("i");
                out.writeAttribute("class", it.getStyleClass());
                out.endTag(true);
                out.appendValue(" " + it.getLabel());
                out.endTag();
                out.endTag();
            }
            out.endTag();
            out.endTag();
        }
        out.endTag();
    }
}
