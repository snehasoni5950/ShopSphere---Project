package com.shopsphere.web.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/**
 * RTU Syllabus: Tag Extensions / Tag Handlers.
 * Formats a numeric price with the Indian Rupee symbol and two decimal places.
 */
public class FormatCurrencyTag extends SimpleTagSupport {

    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.print(String.format("₹%,.2f", value));
    }
}
