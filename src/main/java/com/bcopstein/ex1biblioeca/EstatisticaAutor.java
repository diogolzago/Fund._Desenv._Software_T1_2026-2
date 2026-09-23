package com.bcopstein.ex1biblioeca;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component 
public class EstatisticaAutor {
    private Map<String, Integer> contagem = new HashMap<>(){};

    public void registraConsulta(String autor){
        contagem.merge(autor, 1, Integer::sum);
    }

    public String autorMaisConsultado() {
        return contagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " consultas)")
                .orElse("Nenhuma consulta realizada");
    }

    public String autorMenosConsultado(){
        return contagem.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " consultas)")
                .orElse("Nenhuma consulta realizada");
    }

    public String autorConsulta(){
        String resultado = contagem.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " consultas)")
                .collect(Collectors.joining(", "));
        return resultado.isEmpty() ? "Nenhuma consulta realizada" : resultado;
    }
}
