/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.sql.Timestamp;
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
    private String cedula;
    private Timestamp fechaNacimiento;
    private List<MembresiaClienteDto> membrebesiaCliente = new ArrayList<>();

    public ClienteDto() {
    }

    public ClienteDto(String n) {
        nombre=n;
    }

    public ClienteDto(Long id, String nombre, String apellido1, String apellido2, String cedula, Timestamp fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<MembresiaClienteDto> getMembrebesiaCliente() {
        return membrebesiaCliente;
    }

    public void setMembrebesiaCliente(List<MembresiaClienteDto> membrebesiaCliente) {
        this.membrebesiaCliente = membrebesiaCliente;
    }

}
