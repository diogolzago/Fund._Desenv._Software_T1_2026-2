package com.bcopstein.ex1biblioeca;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "livros")
@Entity
public class Livro{
    @Id
    private long id;
    private String titulo;
    private int ano;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    
    public Livro(){}

    public Livro(long id, String titulo, Autor autor, int ano){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    public long getId(){return this.id;}
    public String getTitulo(){return this.titulo;}
    public Autor getAutor(){return this.autor;}
    public int getAno(){return this.ano;}

    public void setId(long id){this.id = id;}
    public void setTitulo(String titulo){this.titulo=titulo;}
    public void setAutor(Autor autor){this.autor=autor;}
    public void setAno(int ano){this.ano=ano;}
}