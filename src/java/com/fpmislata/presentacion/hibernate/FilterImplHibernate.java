/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.hibernate;

import com.fpmislata.banco.persistence.dao.impl.hibernate.HibernateUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author German
 */
public class FilterImplHibernate implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         try {
            HibernateUtil.openSessionAndBindToThread();
            chain.doFilter(request, response);
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();

        }     
    }

    @Override
    public void destroy() {
       
    }
    
}
