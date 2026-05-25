package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public ResponseEntity<String> cadastrarTutor(Tutor tutor) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(tutor.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(tutor.getEmail());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(tutor);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<String> atualizarTutor(Tutor tutor) {
        repository.save(tutor);
        return ResponseEntity.ok().build();
    }
}
