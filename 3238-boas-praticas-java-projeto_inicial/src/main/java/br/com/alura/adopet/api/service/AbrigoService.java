package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDTO;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private PetRepository petRepository;

    public List<AbrigoDTO> listarTodosOsAbrigos() {
        return repository.findAll()
                .stream()
                .map(AbrigoDTO::new)
                .toList();
    }

    public void cadastrarAbrigo(AbrigoDTO abrigoDTO) {
        boolean nomeJaCadastrado = repository.existsByNome(abrigoDTO.nome());
        boolean telefoneJaCadastrado = repository.existsByTelefone(abrigoDTO.telefone());
        boolean emailJaCadastrado = repository.existsByEmail(abrigoDTO.email());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        repository.save(new Abrigo(abrigoDTO.nome(), abrigoDTO.telefone(), abrigoDTO.email()));
    }

    public List<PetDTO> listarPetsPorId(String idOuNome) {
        Long id = Long.parseLong(idOuNome);
        return repository.getReferenceById(id).getPets()
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    public List<PetDTO> listarPetsPorNome(String idOuNome) {
        return repository.findByNome(idOuNome).getPets()
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    public void cadastrarPetPorId(String idOuNome, PetDTO petDTO) {
        Long id = Long.parseLong(idOuNome);
        Abrigo abrigo = repository.getReferenceById(id);
        associarPetAoAbrigo(abrigo, petDTO);;
    }

    public void cadastrarPetPorNome(String idOuNome, PetDTO petDTO) {
        Abrigo abrigo = repository.findByNome(idOuNome);
        associarPetAoAbrigo(abrigo, petDTO);
    }

    private void associarPetAoAbrigo(Abrigo abrigo, PetDTO petDTO) {
        Pet pet = petRepository.getReferenceById(petDTO.id());
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
        repository.save(abrigo);
    }
}
