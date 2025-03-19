package spring.backend.crud.lurinbooking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.backend.crud.lurinbooking.entities.Ambiente;
import spring.backend.crud.lurinbooking.repositories.AmbienteRepository;

@Service
public class AmbienteService {

    @Autowired 
    private AmbienteRepository ambienteRepository;

    @Transactional(readOnly = true)
    public List<Ambiente> buscaAmbientes(){
        return ambienteRepository.findAll();
    }

    @Transactional
    public Ambiente guardaAmbiente(Ambiente ambiente){
        return ambienteRepository.save(ambiente);
    }
}
