package com.bcopstein.ex1biblioeca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository 
public class Acervo {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    @Autowired 
    public Acervo(LivroRepository livroRepository, AutorRepository autorRepository){
        this.livroRepository=livroRepository;
        this.autorRepository=autorRepository;
    }

    public List<Livro> getAll(){
        return livroRepository.findAll();
    }

    public List<String> getTitulos(){
        return getAll().stream()
                        .map(Livro::getTitulo)
                        .toList();
    }

    public List<String> getAutores(){
        return getAll().stream()
                        .map(livro -> livro.getAutor().toString())
                        .distinct()
                        .toList();
    }

    public List<Livro> getLivrosDoAutor(String autor){
        return livroRepository.findByAutorNome(autor);
    }


    public void cadastraLivroNovo(Livro livro){
        livroRepository.save(livro);
    }

    public boolean deleteLivro(long id){
        if(!livroRepository.existsById(id)) return false;
        livroRepository.deleteById(id);
        return true;
    }

    public List<Autor> getAllAutores(){
        return autorRepository.findAll();
    }

    public Autor getAutorPorNome(String nome){
        return autorRepository.findByNome(nome).orElse(null);
    }
}
