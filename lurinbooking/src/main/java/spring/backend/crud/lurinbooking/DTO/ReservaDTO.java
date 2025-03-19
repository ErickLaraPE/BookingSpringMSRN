package spring.backend.crud.lurinbooking.DTO;

import java.time.LocalDate;
import java.util.List;

public class ReservaDTO {

    private List<Integer> ambientes_id;
    private Integer usuario_id;
    private String tipoAmbReserva;
    private String tipoPago;
    private LocalDate fechaCreacion;
    
    public ReservaDTO() {

    }

    public List<Integer> getAmbientes_id() {
        return ambientes_id;
    }

    public void setAmbientes_id(List<Integer> ambientes_id) {
        this.ambientes_id = ambientes_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTipoAmbReserva() {
        return tipoAmbReserva;
    }

    public void setTipoAmbReserva(String tipoAmbReserva) {
        this.tipoAmbReserva = tipoAmbReserva;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
