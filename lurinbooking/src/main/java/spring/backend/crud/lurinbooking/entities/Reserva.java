package spring.backend.crud.lurinbooking.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer reservaId;

    private String tipoAmbReserva;

    private String tipoPago;

    private LocalDate fechaCreacion;

    @JsonIgnoreProperties({"reservas","handler","hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
        name="reservas_ambientes",
        joinColumns = @JoinColumn(name="reserva_id"),
        inverseJoinColumns = @JoinColumn(name="ambiente_id"),
        uniqueConstraints = {@UniqueConstraint (columnNames = {"reserva_id","ambiente_id"})}
    )
    private List<Ambiente> ambientes;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;


    //SETTERS Y GETTERS

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public Reserva() {
        ambientes = new ArrayList<>();
        fechaCreacion = LocalDate.now();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(List<Ambiente> ambientes) {
        this.ambientes = ambientes;
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
