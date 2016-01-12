/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.controladores;


import com.fpmislata.banco.business.domain.Usuario;
import com.fpmislata.banco.business.service.UsuarioService;
import com.fpmislata.banco.core.BusinessException;
import com.fpmislata.banco.core.BusinessMessage;
import com.fpmislata.banco.security.PasswordManager;
import com.fpmislata.presentacion.json.JsonTransformer;
import com.fpmislata.presentacion.security.WebSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
     @Autowired
     PasswordManager passwordManager;
     
     @Autowired
    UsuarioService usuarioService;
    
     @RequestMapping(value = "/login", method = RequestMethod.POST,consumes= "application/json", produces = "application/json")
    public void login(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        try {
            Usuario usuario = jsonTransformer.fromJSON(jsonEntrada, Usuario.class);
            if (usuario.getNickName().isEmpty() || usuario.getPasswordEncrypt().isEmpty()){
                   throw new BusinessException("Los campos NickName o Password no pueden estar vacios","Usuario");
            }else{
            if(passwordManager.check(usuario.getPasswordEncrypt(), usuarioService.findByNickName(usuario.getNickName()).getPasswordEncrypt() )){
                usuario=usuarioService.findByNickName(usuario.getNickName());
            WebSession webSession = new WebSession(usuario, new Date());
            HttpSession httpSession= httpServletRequest.getSession();
            httpSession.setAttribute("webSession", webSession);
            
            httpServletResponse.getWriter().println(jsonTransformer.toJson(usuario));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }else{
                throw new BusinessException("El password introducido es incorrecto","Usuario");
            }
            }
            
        }catch (BusinessException ex) {
            List<BusinessMessage> businessMessages = ex.getBusinessMessages();
            String jsonSalida = jsonTransformer.toJson(businessMessages);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ex1) {
            ex.printStackTrace(httpServletResponse.getWriter());
        }
    }   
}}
