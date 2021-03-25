package com.example.demo;

import org.example.calculadora.RestarResponse;
import org.example.calculadora.SumarRequest;
import org.example.calculadora.SumarResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
@Endpoint

public class CalculadoraEndPoint {

    @PayloadRoot(namespace= "http://www.example.org/calculadora", localPart= "SumarRequest")
    
    public SumarResponse dameSuma(SumarRequest peticion){
        SumarResponse respuesta =  new SumarResponse();
        respuesta.setResultado( String.valueOf(
            Integer.parseInt(peticion.getA()) +
            Integer.parseInt(peticion.getB())
        ));

        return respuesta;
    }

    public RestarResponse dameResta(RestarResponse peticion){
        RestarResponse respuesta =  new RestarResponse();
        respuesta.setResultado( String.valueOf(
            Integer.parseInt(peticion.getA()) -
            Integer.parseInt(peticion.getB())
        ));

        return respuesta;
    }



}
