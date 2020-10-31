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
public class MembresiaClienteDto {

    private Long id;
    private ClienteDto clienteId;
    private MembresiaDto membresiaId;
    private List<CobroPendienteDto> cobroPendiente = new ArrayList<>();

    @Override
    public String toString() {
        return  membresiaId.getDescripcion();
    }

    public Long getId() {
        return id;
    }

    public ClienteDto getClienteId() {
        return clienteId;
    }

    public MembresiaDto getMembresiaId() {
        return membresiaId;
    }

    public List<CobroPendienteDto> getCobroPendiente() {
        return cobroPendiente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClienteId(ClienteDto clienteId) {
        this.clienteId = clienteId;
    }

    public void setMembresiaId(MembresiaDto membresiaId) {
        this.membresiaId = membresiaId;
    }

    public void setCobroPendiente(List<CobroPendienteDto> cobroPendiente) {
        this.cobroPendiente = cobroPendiente;
    }
    
}
