package com.bcopstein.ex1biblioeca;

import java.util.List;
import java.util.Optional;

public interface AutorRepository {
    <S extends Autor> S save(S autor);
    List<Autor> findAll();
    Optional<Autor> findByNome(String nome);
} 