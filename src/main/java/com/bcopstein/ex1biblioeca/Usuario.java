package com.bcopstein.ex1biblioeca;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Table(name="usuarios")
@Entity
public class Usuario {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    
    @ManyToMany 
    @JoinTable(name="usuario_livros_lidos",
                joinColumns = @JoinColumn (name = "usuario_id"),
                inverseJoinColumns = @JoinColumn (name = "livro_id")
    )
    private Set<Livro> livroslidos = new HashSet<>();

    public Usuario(){}

    public Usuario(String nome, String email){
        this.email=email;
        this.nome=nome;
    }

    public long getId(){return this.id;}
    public String getNome(){return this.nome;}
    public String getEmail(){return this.email;}
    public Set<Livro> getLivrosLidos(){return this.livroslidos;}

    
}
