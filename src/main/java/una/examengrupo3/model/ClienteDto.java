/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LordLalo
 */
public class ClienteDto {
     private Long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private Date fechaNacimiento;
    private List<MembresiaClienteDto> membrebesiaCliente = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public List<MembresiaClienteDto> getMembrebesiaCliente() {
        return membrebesiaCliente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setMembrebesiaCliente(List<MembresiaClienteDto> membrebesiaCliente) {
        this.membrebesiaCliente = membrebesiaCliente;
    }
    
    
}
