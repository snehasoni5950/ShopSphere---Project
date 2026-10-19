package com.shopsphere.web.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/**
 * RTU Syllabus: Tag Extensions / Tag Handlers.
 * Displays a color-coded Bootstrap badge indicating inventory status.
 */
public class StockBadgeTag extends SimpleTagSupport {

    private int stock;

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        if (stock <= 0) {
            out.print("<span class=\"badge bg-danger\">Out of Stock</span>");
        } else if (stock <= 5) {
            out.print("<span class=\"badge bg-warning text-dark\">Only " + stock + " left!</span>");
        } else {
            out.print("<span class=\"badge bg-success\">In Stock</span>");
        }
    }
}
