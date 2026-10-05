package br.com.cauavelho.catalogo;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

public final class Produto {
    private final String codigo;
    private final String nome;
    private final String categoria;
    private final BigDecimal preco;
    private final int quantidade;

    public Produto(String codigo, String nome, String categoria, BigDecimal preco, int quantidade) {
        this.codigo = textoObrigatorio(codigo, "codigo").toUpperCase(Locale.ROOT);
        this.nome = textoObrigatorio(nome, "nome");
        this.categoria = textoObrigatorio(categoria, "categoria");
        this.preco = Objects.requireNonNull(preco, "preco nao pode ser nulo");
        if (preco.signum() < 0) {
            throw new IllegalArgumentException("preco nao pode ser negativo");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("quantidade nao pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    public Produto(String codigo, String nome, String categoria, double preco, int quantidade) {
        this(codigo, nome, categoria, BigDecimal.valueOf(preco), quantidade);
    }

    private static String textoObrigatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " nao pode ser vazio");
        }
        return valor.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorEmEstoque() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ROOT,
                "Codigo: %s | Nome: %s | Categoria: %s | Preco: R$ %s | Quantidade: %d",
                codigo, nome, categoria, preco.toPlainString(), quantidade);
    }
}
