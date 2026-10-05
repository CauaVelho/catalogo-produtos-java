package br.com.cauavelho.catalogo;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class CatalogoProdutos {
    private final Produto[] produtos;
    private int quantidadeCadastrada;

    public CatalogoProdutos(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("capacidade deve ser maior que zero");
        }
        this.produtos = new Produto[capacidade];
    }

    public void inserir(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("produto nao pode ser nulo");
        }
        if (quantidadeCadastrada == produtos.length) {
            throw new IllegalStateException("catalogo cheio: capacidade " + produtos.length);
        }
        if (buscarPorCodigo(produto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("ja existe um produto com o codigo " + produto.getCodigo());
        }
        produtos[quantidadeCadastrada++] = produto;
    }

    public Optional<Produto> buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return Optional.empty();
        }
        String codigoNormalizado = codigo.trim();
        for (int i = 0; i < quantidadeCadastrada; i++) {
            if (produtos[i].getCodigo().equalsIgnoreCase(codigoNormalizado)) {
                return Optional.of(produtos[i]);
            }
        }
        return Optional.empty();
    }

    public List<Produto> listarTodos() {
        return List.copyOf(Arrays.asList(Arrays.copyOf(produtos, quantidadeCadastrada)));
    }

    public List<Produto> buscarPorNomeOuCategoria(String consulta) {
        if (consulta == null || consulta.trim().isEmpty()) {
            return List.of();
        }
        String termo = normalizar(consulta);
        List<Produto> encontrados = new ArrayList<>();
        for (int i = 0; i < quantidadeCadastrada; i++) {
            Produto produto = produtos[i];
            if (normalizar(produto.getNome()).contains(termo)
                    || normalizar(produto.getCategoria()).contains(termo)) {
                encontrados.add(produto);
            }
        }
        return List.copyOf(encontrados);
    }

    public List<Produto> listarAbaixoDaQuantidade(int limite) {
        if (limite < 0) {
            throw new IllegalArgumentException("limite nao pode ser negativo");
        }
        List<Produto> encontrados = new ArrayList<>();
        for (int i = 0; i < quantidadeCadastrada; i++) {
            if (produtos[i].getQuantidade() < limite) {
                encontrados.add(produtos[i]);
            }
        }
        return List.copyOf(encontrados);
    }

    public BigDecimal calcularValorTotalEmEstoque() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < quantidadeCadastrada; i++) {
            total = total.add(produtos[i].getValorEmEstoque());
        }
        return total;
    }

    public int getQuantidadeCadastrada() {
        return quantidadeCadastrada;
    }

    public int getCapacidade() {
        return produtos.length;
    }

    private static String normalizar(String texto) {
        String semAcentos = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        return semAcentos.toLowerCase(Locale.ROOT);
    }
}
