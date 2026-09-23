package com.bcopstein.ex1biblioeca;

import java.util.List;
import java.util.Optional;

public interface LivroRepository {
    List<Livro> findAll();
    List<Livro> findByAutorNome(String autor);
    <S extends Livro> S save(S livro);
    boolean existsById(Long id);
    void deleteById(Long id);
    Optional<Livro> findById(Long id);
}

