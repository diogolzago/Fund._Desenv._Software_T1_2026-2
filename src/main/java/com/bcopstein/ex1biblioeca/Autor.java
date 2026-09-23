package com.bcopstein.ex1biblioeca;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="autores")
@Entity 
public class Autor {
    @Id 
    private long id;
    private String nome;
    private LocalDate dataNascimento;
    
    public Autor(){}

    public Autor(long id, String nome, LocalDate dataNascimento){
        this.id=id;
        this.nome=nome;
        this.dataNascimento=dataNascimento;
    }

    public long getId(){return this.id;}
    public String getNome(){return this.nome;}
    public LocalDate getDataNascimento(){return this.dataNascimento;}

    @Override 
    public String toString(){
        return "Autor: [id=" +id+
        ", nome="+nome+
        ", dataNascimento="+dataNascimento+"]";
    }
}
