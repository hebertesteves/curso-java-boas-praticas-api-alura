package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.TutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public void cadastrarTutor(TutorDTO tutorDTO) {
        boolean jaCadastrado = repository.existsByTelefoneOrEmail(tutorDTO.telefone(), tutorDTO.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }

        repository.save(new Tutor(tutorDTO.nome(), tutorDTO.email(), tutorDTO.telefone()));
    }

    public void atualizarTutor(TutorDTO tutorDTO) {
        Tutor tutor = repository.getReferenceById(tutorDTO.id());
        tutor.setNome(tutorDTO.nome());
        tutor.setEmail(tutorDTO.email());
        tutor.setTelefone(tutorDTO.telefone());

        repository.save(tutor);
    }
}
