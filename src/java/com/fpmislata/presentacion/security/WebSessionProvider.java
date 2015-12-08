/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author German
 */
public interface WebSessionProvider {
    WebSession getWebSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
