package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public ResponseEntity<List<PetDTO>> listarTodosOsPetsDisponiveis() {
        List<Pet> pets = repository.findAll();
        List<PetDTO> disponiveis = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getAdotado() == false) {
                disponiveis.add(new PetDTO(pet));
            }
        }
        return ResponseEntity.ok(disponiveis);
    }
}
