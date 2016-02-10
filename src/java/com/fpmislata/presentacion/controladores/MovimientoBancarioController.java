/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.controladores;


import com.fpmislata.banco.business.domain.MovimientoBancario;
import com.fpmislata.banco.business.service.MovimientoBancarioService;
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
public class MovimientoBancarioController {

    @Autowired
    MovimientoBancarioService movimientoBancarioService;
    @Autowired
    JsonTransformer jsonTransformer;

    @RequestMapping(value = "/movimientobancario", method = RequestMethod.GET, produces = "application/json")
    public void findall(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            String jsonUsuario;
            if (httpServletRequest.getParameter("idCuentaBancaria") != null) {
                jsonUsuario = jsonTransformer.toJson(movimientoBancarioService.findByidCuentaBancaria(Integer.parseInt(httpServletRequest.getParameter("idCuentaBancaria"))));
            } else {
                jsonUsuario = jsonTransformer.toJson(movimientoBancarioService.findAll());
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

    @RequestMapping(value = "/movimientobancario/{idMovimientoBancario}", method = RequestMethod.GET, produces = "application/json")
    public void get(@PathVariable("idMovimientoBancario") int idMovimientoBancario, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            MovimientoBancario movimientoBancaria = movimientoBancarioService.get(idMovimientoBancario);

            if (movimientoBancaria == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                String jsonUsuario = jsonTransformer.toJson(movimientoBancaria);
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

    @RequestMapping(value = "/movimientobancario/{idMovimientoBancario}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("idMovimientoBancario") int idMovimientoBancario, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            boolean response = movimientoBancarioService.delete(idMovimientoBancario);

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

    @RequestMapping(value = "/movimientobancario", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insert(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            MovimientoBancario movimientoBancario = jsonTransformer.fromJSON(jsonEntrada, MovimientoBancario.class);
            String jsonSalida = jsonTransformer.toJson(movimientoBancarioService.insert(movimientoBancario));
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

    @RequestMapping(value = "/movimientobancario", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void update(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            MovimientoBancario movimientoBancario = jsonTransformer.fromJSON(jsonEntrada, MovimientoBancario.class);
            String jsonSalida = jsonTransformer.toJson(movimientoBancarioService.update(movimientoBancario));
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
