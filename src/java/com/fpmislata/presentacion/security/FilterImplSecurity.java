/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.security;

import com.fpmislata.banco.business.domain.Usuario;
import com.fpmislata.banco.security.Authorization;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;





/**
 *
 * @author German
 */
public class FilterImplSecurity implements Filter{
    @Autowired
    WebSessionProvider webSessionProvider;
    @Autowired
    Authorization authorization;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
      AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
      autowireCapableBeanFactory.autowireBean(this);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      Usuario usuario;  
       
      HttpServletRequest httpServletRequest =(HttpServletRequest) request;
      HttpServletResponse httpServletResponse =(HttpServletResponse) response;
      WebSession webSession=webSessionProvider.getWebSession(httpServletRequest, httpServletResponse);
      if(webSession == null){
          usuario = null;
      }else{
          usuario= webSession.getUsuario();
      }
      if(authorization.isAuthorizedURL(usuario,httpServletRequest.getRequestURI() ,httpServletRequest.getMethod())){
        chain.doFilter(request, response);
      }else{
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);   
      }
      
    }

    @Override
    public void destroy() {
        
    }

    

    
    
}
