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
public class MembresiaDto {

    private boolean estado;
    private Long id;
    private String periodicidad;
    private Integer monto;
    private String descripcion;
    private ServicioDto servicioId;
    private List<MembresiaClienteDto> membresiaCliente = new ArrayList<>();

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

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

    public void setServicioId(ServicioDto servicioId) {
        this.servicioId = servicioId;
    }

    public void setMembresiaCliente(List<MembresiaClienteDto> membresiaCliente) {
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

    public ServicioDto getServicioId() {
        return servicioId;
    }

    public List<MembresiaClienteDto> getMembresiaCliente() {
        return membresiaCliente;
    }

}
