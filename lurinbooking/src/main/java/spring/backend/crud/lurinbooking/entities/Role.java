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

@Entity
@Table(name="roles")

public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")

    private Integer roleId;

    private String name;

    @JsonIgnoreProperties({"roles","handler","hibernateLazyInitializer"}) // propiedad para que ignore al atributo lista de roles de la entidad user y asi evitar un bucle infinito
    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    public Integer getId() {
        return roleId;
    }

    public void setId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role() {
    
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
