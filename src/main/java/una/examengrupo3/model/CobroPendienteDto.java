/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.util.Date;

/**
 *
 * @author LordLalo
 */
public class CobroPendienteDto {

    private Long id;
    private String anno;
    private Date fechaVencimiento;
    private Integer monto;
    private boolean estado;
    private String periodo;
    private MembresiaClienteDto membresiaClienteId;
    private Integer cantidad;

    public CobroPendienteDto() {
    }

    public CobroPendienteDto(String anno, Date fechaVencimiento, Integer monto, boolean estado, String periodo, MembresiaClienteDto membresiaClienteId,Integer cantidad) {

        this.anno = anno;
        this.fechaVencimiento = fechaVencimiento;
        this.monto = monto;
        this.estado = estado;
        this.periodo = periodo;
        this.membresiaClienteId = membresiaClienteId;
        this.cantidad=cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public MembresiaClienteDto getMembresiaClienteId() {
        return membresiaClienteId;
    }

    public void setMembresiaClienteId(MembresiaClienteDto membresiaClienteId) {
        this.membresiaClienteId = membresiaClienteId;
    }

}
