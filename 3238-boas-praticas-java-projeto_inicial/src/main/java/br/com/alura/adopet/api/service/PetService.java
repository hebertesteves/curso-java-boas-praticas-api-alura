package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public ResponseEntity<List<PetDTO>> listarTodosOsPetsDisponiveis() {
        List<PetDTO> disponiveis = repository.findAllByAdotadoFalse()
                .stream()
                .map(PetDTO::new)
                .toList();

        return ResponseEntity.ok(disponiveis);
    }
}
