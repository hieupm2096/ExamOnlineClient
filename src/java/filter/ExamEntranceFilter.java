/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import model.ExamEntranceBean;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author oswal
 */
@WebFilter(filterName = "ExamEntranceFilter", urlPatterns = {"/app/exam/*"})
public class ExamEntranceFilter implements Filter {
    
    @Inject
    private ExamEntranceBean examEntranceBean;
    
    private static final String EXAM_ENTRANCE_PAGE_REDIRECT = "/app/exam-entrance.xhtml";

    public ExamEntranceFilter() {
    }

    /**
     *
     * @param req The servlet request we are processing
     * @param res The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (examEntranceBean == null || !examEntranceBean.isAccessedExam()) {
            response.sendRedirect(request.getContextPath() + EXAM_ENTRANCE_PAGE_REDIRECT);
        }
        chain.doFilter(req, res);
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
    }

}
