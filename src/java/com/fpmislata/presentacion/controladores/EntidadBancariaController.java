/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.controladores;

import com.fpmislata.banco.business.domain.EntidadBancaria;
import com.fpmislata.banco.business.service.EntidadBancariaService;
import com.fpmislata.banco.core.BusinessException;
import com.fpmislata.banco.core.BusinessMessage;
import com.fpmislata.banco.util.json.JsonTransformer;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author German
 */
@Controller
public class EntidadBancariaController {

    @Autowired
    EntidadBancariaService entidadBancariaService;
    @Autowired
    JsonTransformer jsonTransformer;

    @RequestMapping(value = "/entidadbancaria", method = RequestMethod.GET, produces = "application/json")
    public void findall(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            String jsonUsuario;
            if (httpServletRequest.getParameter("nombre") != null) {
                jsonUsuario = jsonTransformer.toJson(entidadBancariaService.findByNombre(httpServletRequest.getParameter("nombre")));
            } else {
                jsonUsuario = jsonTransformer.toJson(entidadBancariaService.findAll());
            }

            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonUsuario);

        } catch (BusinessException ex) {
            List<BusinessMessage> businessMessages = ex.getBusinessMessages();
            String jsonSalida = jsonTransformer.toJson(businessMessages);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    @RequestMapping(value = "/entidadbancaria/{idEntidadBancaria}", method = RequestMethod.GET, produces = "application/json")
    public void get(@PathVariable("idEntidadBancaria") int idEntidadBancaria, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            EntidadBancaria entidadBancaria = entidadBancariaService.get(idEntidadBancaria);

            if (entidadBancaria == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                String jsonUsuario = jsonTransformer.toJson(entidadBancaria);
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.getWriter().println(jsonUsuario);
            }

        } catch (BusinessException ex) {
            List<BusinessMessage> businessMessages = ex.getBusinessMessages();
            String jsonSalida = jsonTransformer.toJson(businessMessages);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @RequestMapping(value = "/entidadbancaria/defaultValue", method = RequestMethod.GET, produces = "application/json")
    public void defaultValue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            EntidadBancaria entidadBancaria = entidadBancariaService.defaultValue();

            if (entidadBancaria == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                String jsonUsuario = jsonTransformer.toJson(entidadBancaria);
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.getWriter().println(jsonUsuario);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @RequestMapping(value = "/entidadbancaria/{idEntidadBancaria}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("idEntidadBancaria") int idEntidadBancaria, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            boolean response = entidadBancariaService.delete(idEntidadBancaria);

            if (response) {
                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (BusinessException ex) {
            List<BusinessMessage> businessMessages = ex.getBusinessMessages();
            String jsonSalida = jsonTransformer.toJson(businessMessages);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @RequestMapping(value = "/entidadbancaria", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insert(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            EntidadBancaria entidadBancaria = jsonTransformer.fromJSON(jsonEntrada, EntidadBancaria.class);
            String jsonSalida = jsonTransformer.toJson(entidadBancariaService.insert(entidadBancaria));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BusinessException ex) {
            List<BusinessMessage> businessMessages = ex.getBusinessMessages();
            String jsonSalida = jsonTransformer.toJson(businessMessages);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @RequestMapping(value = "/entidadbancaria", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void update(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            EntidadBancaria entidadBancaria = jsonTransformer.fromJSON(jsonEntrada, EntidadBancaria.class);
            String jsonSalida = jsonTransformer.toJson(entidadBancariaService.update(entidadBancaria));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BusinessException ex) {
            List<BusinessMessage> businessMessages = ex.getBusinessMessages();
            String jsonSalida = jsonTransformer.toJson(businessMessages);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(EntidadBancariaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

}
