package br.com.cauavelho.catalogo;

import java.math.BigDecimal;
import java.util.List;

public final class CatalogoProdutosTest {
    private static int verificacoes;

    private CatalogoProdutosTest() {
    }

    public static void main(String[] args) {
        validarConstrutoresEEntradas();
        validarCatalogo();
        validarCapacidadesVariadas();
        System.out.println("OK: " + verificacoes + " verificacoes passaram.");
    }

    private static void validarConstrutoresEEntradas() {
        Produto produto = new Produto(" p-1 ", "  Café  ", " Bebidas ", new BigDecimal("12.50"), 4);
        verificar("P-1".equals(produto.getCodigo()), "codigo deve ser aparado e normalizado");
        verificar("Café".equals(produto.getNome()), "nome deve remover espacos externos");
        verificar(new BigDecimal("50.00").compareTo(produto.getValorEmEstoque()) == 0,
                "valor em estoque deve usar aritmetica decimal");
        verificarFalha(() -> new Produto(" ", "Nome", "Categoria", 1.0, 1),
                IllegalArgumentException.class, "codigo vazio deve ser rejeitado");
        verificarFalha(() -> new Produto("1", "Nome", "Categoria", -1.0, 1),
                IllegalArgumentException.class, "preco negativo deve ser rejeitado");
        verificarFalha(() -> new Produto("1", "Nome", "Categoria", 1.0, -1),
                IllegalArgumentException.class, "quantidade negativa deve ser rejeitada");
        verificarFalha(() -> new Produto("1", "Nome", "Categoria", Double.NaN, 1),
                NumberFormatException.class, "preco nao finito deve ser rejeitado");
    }

    private static void validarCatalogo() {
        CatalogoProdutos catalogo = new CatalogoProdutos(4);
        Produto cafe = new Produto("B01", "Café especial", "Bebidas", new BigDecimal("15.90"), 3);
        Produto cha = new Produto("B02", "Cha verde", "Bebidas", new BigDecimal("8.00"), 1);
        Produto caderno = new Produto("P01", "Caderno", "Papelaria", new BigDecimal("12.50"), 10);
        catalogo.inserir(cafe);
        catalogo.inserir(cha);
        catalogo.inserir(caderno);

        verificar(catalogo.getQuantidadeCadastrada() == 3, "quantidade cadastrada deve ser atualizada");
        verificar(catalogo.buscarPorCodigo("b01").orElseThrow() == cafe, "consulta de codigo deve ignorar caixa");
        verificar(catalogo.buscarPorCodigo("inexistente").isEmpty(), "codigo inexistente deve retornar vazio");
        verificar(catalogo.buscarPorNomeOuCategoria("CAFE").equals(List.of(cafe)),
                "busca parcial deve ignorar caixa e acentos");
        verificar(catalogo.buscarPorNomeOuCategoria("bebidas").size() == 2,
                "busca deve considerar categorias");
        verificar(catalogo.listarAbaixoDaQuantidade(4).equals(List.of(cafe, cha)),
                "filtro deve retornar apenas quantidades inferiores ao limite");
        verificar(catalogo.calcularValorTotalEmEstoque().compareTo(new BigDecimal("180.70")) == 0,
                "total em estoque deve somar preco vezes quantidade");
        verificar(catalogo.listarTodos().size() == 3, "listagem deve conter os produtos inseridos");
        verificarFalha(() -> catalogo.inserir(new Produto("b01", "Duplicado", "Teste", 1.0, 1)),
                IllegalArgumentException.class, "codigo duplicado deve ser rejeitado sem diferenciar caixa");
        verificarFalha(() -> catalogo.listarTodos().clear(),
                UnsupportedOperationException.class, "listagem nao deve permitir alteracao externa");
        verificarFalha(() -> catalogo.inserir(null),
                IllegalArgumentException.class, "produto nulo deve ser rejeitado");
        verificarFalha(() -> catalogo.listarAbaixoDaQuantidade(-1),
                IllegalArgumentException.class, "limite negativo deve ser rejeitado");
    }

    private static void validarCapacidadesVariadas() {
        int[] tamanhos = {1, 10, 1_000};
        for (int capacidade : tamanhos) {
            CatalogoProdutos catalogo = new CatalogoProdutos(capacidade);
            for (int i = 0; i < capacidade; i++) {
                catalogo.inserir(new Produto(
                        "P" + i, "Produto " + i, "Testes", BigDecimal.ONE, i));
            }
            verificar(catalogo.getQuantidadeCadastrada() == capacidade,
                    "deve cadastrar todos os itens para capacidade " + capacidade);
            verificar(catalogo.buscarPorNomeOuCategoria("produto").size() == capacidade,
                    "busca deve percorrer todos os itens para capacidade " + capacidade);
            verificarFalha(
                    () -> catalogo.inserir(new Produto("EXTRA", "Extra", "Testes", 1.0, 1)),
                    IllegalStateException.class,
                    "catalogo deve rejeitar insercao alem da capacidade " + capacidade);
        }
        verificarFalha(() -> new CatalogoProdutos(0),
                IllegalArgumentException.class, "capacidade zero deve ser rejeitada");
    }

    private static void verificarFalha(
            Runnable acao, Class<? extends RuntimeException> tipoEsperado, String mensagem) {
        try {
            acao.run();
        } catch (RuntimeException exception) {
            if (tipoEsperado.isInstance(exception)) {
                verificar(true, mensagem);
                return;
            }
            throw new AssertionError(mensagem + ": tipo inesperado " + exception.getClass().getName(), exception);
        }
        throw new AssertionError(mensagem + ": nenhuma excecao foi lancada");
    }

    private static void verificar(boolean condicao, String mensagem) {
        verificacoes++;
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
