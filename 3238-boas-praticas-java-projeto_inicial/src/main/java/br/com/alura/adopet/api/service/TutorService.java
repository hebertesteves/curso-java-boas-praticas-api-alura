package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.TutorDTO;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public ResponseEntity<String> cadastrarTutor(TutorDTO tutorDTO) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(tutorDTO.telefone());
        boolean emailJaCadastrado = repository.existsByEmail(tutorDTO.email());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(new Tutor(tutorDTO.nome(), tutorDTO.email(), tutorDTO.telefone()));
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<String> atualizarTutor(TutorDTO tutorDTO) {
        repository.save(new Tutor(tutorDTO.nome(), tutorDTO.email(), tutorDTO.telefone()));
        return ResponseEntity.ok().build();
    }
}
