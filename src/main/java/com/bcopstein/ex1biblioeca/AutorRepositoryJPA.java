package com.bcopstein.ex1biblioeca;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface AutorRepositoryJPA extends JpaRepository<Autor,Long>, AutorRepository{
}
