package spring.backend.crud.lurinbooking.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="ambientes")

public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ambiente_id")
    private Integer ambienteId;

    @NotBlank
    private String name;

    @NotNull
    private Integer price;

    @JsonIgnoreProperties({"ambientes","handler","hibernateLazyInitializer"}) // propiedad para que ignore al atributo lista de roles de la entidad user y asi evitar un bucle infinito
    @ManyToMany(mappedBy = "ambientes")
    private List<Reserva> reservas;

    public Integer getAmbienteId() {
        return ambienteId;
    }

    public void setAmbienteId(Integer ambienteId) {
        this.ambienteId = ambienteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Ambiente() {

    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
