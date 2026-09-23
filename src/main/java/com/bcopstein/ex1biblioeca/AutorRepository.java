package com.bcopstein.ex1biblioeca;

import java.util.List;
import java.util.Optional;

public interface AutorRepository {
    List<Autor> findAll();
    Optional<Autor> findByNome(String nome);
} 