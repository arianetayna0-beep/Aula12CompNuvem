package com.senai.aula07api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ProdutoController {

    private final List<Produto> produtos = new CopyOnWriteArrayList<>(List.of(
            new Produto(1L, "Notebook", 3500),
            new Produto(2L, "Mouse", 100),
            new Produto(3L, "Teclado", 250)
    ));

    @GetMapping("/")
    public String home() {
        return "Aula 12 - Computação em Nuvem - Deploy na Railway";
    }

    @GetMapping("/produtos")
    public List<Produto> listar() {
        return produtos;
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> buscar(@PathVariable Long id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/produtos")
    public Produto criar(@RequestBody Produto produto) {
        produtos.add(produto);
        return produto;
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto novo) {
        for (Produto p : produtos) {
            if (p.getId().equals(id)) {
                p.setNome(novo.getNome());
                p.setPreco(novo.getPreco());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean removido = produtos.removeIf(p -> p.getId().equals(id));
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}