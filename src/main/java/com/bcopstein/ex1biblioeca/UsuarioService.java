package com.bcopstein.ex1biblioeca;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bcopstein.ex1biblioeca.DTO.CriarUsuarioRequest;
import com.bcopstein.ex1biblioeca.DTO.LivroResponse;
import com.bcopstein.ex1biblioeca.DTO.UsuarioResponse;

import jakarta.transaction.Transactional;

@Service 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public UsuarioService(UsuarioRepository usuarioERepository, LivroRepository livroRepository){
        this.usuarioRepository=usuarioERepository;
        this.livroRepository=livroRepository;
    }

    private Usuario buscarUsuario(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não encontrado"));
    }

    public UsuarioResponse Criar(CriarUsuarioRequest req){
        Usuario salvo = usuarioRepository.save(new Usuario(req.nome(), req.email()));
        return UsuarioResponse.de(salvo);
    }
    public UsuarioResponse buscar(long id){
        return UsuarioResponse.de(buscarUsuario(id));
    }
    @Transactional public List<LivroResponse> livrosLidos(long id){
        return buscarUsuario(id).getLivrosLidos().stream()
                .map(LivroResponse::de)
                .toList();
    }
    @Transactional public void marcarLido(long usuarioId, long livroId){
        Usuario usuario = buscarUsuario(usuarioId);
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "livro não encontrado"));
        usuario.getLivrosLidos().add(livro);
    }

}
