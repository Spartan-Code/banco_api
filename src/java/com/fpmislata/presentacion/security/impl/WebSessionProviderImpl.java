/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.security.impl;


import com.fpmislata.presentacion.security.WebSession;
import com.fpmislata.presentacion.security.WebSessionProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author German
 */
public class WebSessionProviderImpl implements WebSessionProvider {
    
    @Override
    public WebSession getWebSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        WebSession webSession;
        HttpSession httpSession = httpServletRequest.getSession();
        webSession =(WebSession)httpSession.getAttribute("webSession");      
        return webSession;
    }
    
}
