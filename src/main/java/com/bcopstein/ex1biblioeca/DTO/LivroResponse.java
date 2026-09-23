package com.bcopstein.ex1biblioeca.DTO;
import com.bcopstein.ex1biblioeca.Livro;

public record LivroResponse(long id, String titulo, int ano, String autor){
    public static LivroResponse de(Livro l){return new LivroResponse(l.getId(), l.getTitulo(), l.getAno(), l.getAutor().getNome());}
}