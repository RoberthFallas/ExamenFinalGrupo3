/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LordLalo
 */
public class MembresiaDTO {
    private Long id;
    private String periodicidad;
    private Integer monto;
    private String descripcion;
    private ServicioDTO servicioId;
    private List<MembresiaClienteDTO> membresiaCliente = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setServicioId(ServicioDTO servicioId) {
        this.servicioId = servicioId;
    }

    public void setMembresiaCliente(List<MembresiaClienteDTO> membresiaCliente) {
        this.membresiaCliente = membresiaCliente;
    }

    public Long getId() {
        return id;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public Integer getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ServicioDTO getServicioId() {
        return servicioId;
    }

    public List<MembresiaClienteDTO> getMembresiaCliente() {
        return membresiaCliente;
    }
    
}
