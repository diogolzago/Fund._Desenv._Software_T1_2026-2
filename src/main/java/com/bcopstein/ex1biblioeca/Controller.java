package com.bcopstein.ex1biblioeca;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex1biblioeca.DTO.CriarUsuarioRequest;
import com.bcopstein.ex1biblioeca.DTO.LivroResponse;
import com.bcopstein.ex1biblioeca.DTO.UsuarioResponse;

import jakarta.validation.Valid;

import java.util.List;

@RestController 
public class Controller {
    private Acervo acervo;
    private EstatisticaAutor estatistica;
    private UsuarioService usuarioService;

    @Autowired
    public Controller(Acervo acervo, EstatisticaAutor estatistica, UsuarioService usuarioService){
        this.acervo = acervo;
        this.estatistica=estatistica;
        this.usuarioService=usuarioService;
    }

    @GetMapping()
    public String getSaudacao(){
        return "Bem vindo a Biblioteca Central!!";
    }

    @GetMapping("/livros")
    public List<Livro> getLivros(){
        return acervo.getAll();
    }

    @GetMapping("/titulos")
    public List<String> getTitulos(){
        return acervo.getTitulos();
    }

    @GetMapping("/autores/nomes")
    public List<String> getAutoresNomes(){
        return acervo.getAutores();
    }

    @PostMapping("/livros/cadastro")
    public ResponseEntity<Livro> cadastraLivroNovo(@RequestBody Livro livro){
        acervo.cadastraLivroNovo(livro);
        return ResponseEntity
            .created(URI.create("/livros/"+ livro.getId()))
            .body(livro);
    }

    @DeleteMapping("/livros/delete/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable long id){
        return acervo.deleteLivro(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/autorMaisConsultado")
    public String maisConsutlado(){
        return estatistica.autorMaisConsultado();
    }

    @GetMapping("/autorMenosConsultado")
    public String menosConsutlado(){
        return estatistica.autorMenosConsultado();
    }

    @GetMapping("/quantConsult")
    public String quantConsutl(){
        return estatistica.autorConsulta();
    }

    @GetMapping("/livros/{autor}")
    public List<Livro> getBuscaAutor(@PathVariable(value="autor") String autor){
        List<Livro> aux = acervo.getLivrosDoAutor(autor);
        if(!aux.isEmpty()) estatistica.registraConsulta(autor); 
        return acervo.getLivrosDoAutor(autor);
    }

    @GetMapping("/autores/{autor}")
    public Autor infoAutor(@PathVariable(value="autor") String autor){
        Autor aux = acervo.getAutorPorNome(autor);
        if(aux!=null) estatistica.registraConsulta(autor);
        return aux;
    }

    @GetMapping("/autores/info")
    public List<Autor> infoAllAutores(){
        return acervo.getAllAutores();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> criarUsuario(@Valid @RequestBody CriarUsuarioRequest req){
        UsuarioResponse criado = usuarioService.Criar(req);
        return ResponseEntity
            .created(URI.create("/usuarios/" + criado.id()))
            .body(criado);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public UsuarioResponse buscarUsuario(@PathVariable long usuarioId){
        return usuarioService.buscar(usuarioId);
    }

    @GetMapping("/usuarios/{usuarioId}/livros-lidos")
    public List<LivroResponse> livrosLidos(@PathVariable long usuarioId){
        return usuarioService.livrosLidos(usuarioId);
    }

    @PutMapping("/usuarios/{usuarioId}/livros-lidos/{livroId}")
    public ResponseEntity<Void> marcarLido(@PathVariable long usuarioId, @PathVariable long livroId){
        usuarioService.marcarLido(usuarioId, livroId);
        return ResponseEntity.noContent().build();
    }
}
