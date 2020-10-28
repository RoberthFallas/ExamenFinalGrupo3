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
public class MembresiaClienteDTO {

    private Long id;
    private ClienteDTO clienteId;
    private MembresiaDTO membresiaId;
    private List<CobroPendienteDTO> cobroPendiente = new ArrayList<>();

    @Override
    public String toString() {
        return  membresiaId.getDescripcion();
    }

    public Long getId() {
        return id;
    }

    public ClienteDTO getClienteId() {
        return clienteId;
    }

    public MembresiaDTO getMembresiaId() {
        return membresiaId;
    }

    public List<CobroPendienteDTO> getCobroPendiente() {
        return cobroPendiente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClienteId(ClienteDTO clienteId) {
        this.clienteId = clienteId;
    }

    public void setMembresiaId(MembresiaDTO membresiaId) {
        this.membresiaId = membresiaId;
    }

    public void setCobroPendiente(List<CobroPendienteDTO> cobroPendiente) {
        this.cobroPendiente = cobroPendiente;
    }
    
}
