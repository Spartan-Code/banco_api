/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.controladores;

import com.fpmislata.banco.business.domain.MovimientoBancario;
import com.fpmislata.banco.business.domain.Tipo;
import com.fpmislata.banco.business.domain.Transaccion;
import com.fpmislata.banco.business.service.CuentaBancariaService;
import com.fpmislata.banco.business.service.MovimientoBancarioService;
import com.fpmislata.banco.core.BusinessException;
import com.fpmislata.banco.core.BusinessMessage;
import com.fpmislata.presentacion.json.JsonTransformer;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author PEDRO DEL BARRIO
 */
@Controller
public class TransaccionController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    CuentaBancariaService cuentaBancariaService;

    @Autowired
    MovimientoBancarioService movimientoBancarioService;

    @RequestMapping(value = "/transaccion", method = RequestMethod.POST, consumes = "application/json")
    public void doTransaccion(@RequestBody String jsonEntrada, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        try {
            Transaccion transaccion = jsonTransformer.fromJSON(jsonEntrada, Transaccion.class);
            
            String numeroCuentaOrigen = transaccion.getCuentaOrigen().substring(10);
            String numeroCuentaDestino = transaccion.getCuentaDestino().substring(10);
            
            MovimientoBancario movimientoBancarioCuentaOrigen = new MovimientoBancario();
            movimientoBancarioCuentaOrigen.setFecha(new Date());
            movimientoBancarioCuentaOrigen.setConcepto(transaccion.getConcepto());
            movimientoBancarioCuentaOrigen.setTipo(Tipo.Debe);
            movimientoBancarioCuentaOrigen.setImporte(transaccion.getImporte());
            movimientoBancarioCuentaOrigen.setCuentaBancaria(cuentaBancariaService.findByNumeroCuenta(numeroCuentaOrigen));
            movimientoBancarioService.insert(movimientoBancarioCuentaOrigen);
            
            MovimientoBancario movimientoBancarioCuentaDestino = new MovimientoBancario();
            movimientoBancarioCuentaDestino.setFecha(new Date());
            movimientoBancarioCuentaDestino.setConcepto(transaccion.getConcepto());
            movimientoBancarioCuentaDestino.setTipo(Tipo.Haber);
            movimientoBancarioCuentaDestino.setImporte(transaccion.getImporte());
            movimientoBancarioCuentaDestino.setCuentaBancaria(cuentaBancariaService.findByNumeroCuenta(numeroCuentaDestino));
            movimientoBancarioService.insert(movimientoBancarioCuentaDestino);

            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);

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
