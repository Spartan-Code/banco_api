/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.controladores;


import com.fpmislata.banco.business.domain.Usuario;
import com.fpmislata.presentacion.json.JsonTransformer;
import com.fpmislata.presentacion.security.WebSession;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author German
 */
@Controller
public class LoginController {
    @Autowired
    JsonTransformer jsonTransformer;
     @RequestMapping(value = "/login", method = RequestMethod.POST,consumes= "application/json", produces = "application/json")
    public void login(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        try {
            Usuario usuario = jsonTransformer.fromJSON(jsonEntrada, Usuario.class);
            if (usuario == null){
                 httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }else{
            WebSession webSession = new WebSession(usuario, new Date());
            HttpSession httpSession= httpServletRequest.getSession();
            httpSession.setAttribute("webSession", webSession);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace(httpServletResponse.getWriter());
        }
    }
}
