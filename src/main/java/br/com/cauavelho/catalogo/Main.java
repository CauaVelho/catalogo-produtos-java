package br.com.cauavelho.catalogo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Main {
    private static final int CAPACIDADE_PADRAO = 100;

    private Main() {
    }

    public static void main(String[] args) {
        CatalogoProdutos catalogo = new CatalogoProdutos(CAPACIDADE_PADRAO);
        try (Scanner scanner = new Scanner(System.in)) {
            boolean executando = true;
            while (executando) {
                exibirMenu();
                String opcao = lerTexto(scanner, "Opcao: ");
                try {
                    switch (opcao) {
                        case "1" -> inserirProduto(scanner, catalogo);
                        case "2" -> listar(catalogo.listarTodos());
                        case "3" -> consultarCodigo(scanner, catalogo);
                        case "4" -> pesquisar(scanner, catalogo);
                        case "5" -> filtrarEstoque(scanner, catalogo);
                        case "6" -> System.out.println(
                                "Valor total em estoque: R$ " + catalogo.calcularValorTotalEmEstoque().toPlainString());
                        case "0" -> executando = false;
                        default -> System.out.println("Opcao invalida.");
                    }
                } catch (IllegalArgumentException | IllegalStateException exception) {
                    System.out.println("Erro: " + exception.getMessage());
                }
                System.out.println();
            }
        }
        System.out.println("Catalogo encerrado.");
    }

    private static void exibirMenu() {
        System.out.println("""
                === Catalogo de Produtos ===
                1. Cadastrar produto
                2. Listar produtos
                3. Consultar por codigo
                4. Pesquisar nome ou categoria
                5. Listar produtos abaixo de uma quantidade
                6. Calcular valor total em estoque
                0. Sair
                """);
    }

    private static void inserirProduto(Scanner scanner, CatalogoProdutos catalogo) {
        String codigo = lerTexto(scanner, "Codigo: ");
        String nome = lerTexto(scanner, "Nome: ");
        String categoria = lerTexto(scanner, "Categoria: ");
        BigDecimal preco = lerDecimal(scanner, "Preco: R$ ");
        int quantidade = lerInteiro(scanner, "Quantidade: ");
        catalogo.inserir(new Produto(codigo, nome, categoria, preco, quantidade));
        System.out.println("Produto cadastrado.");
    }

    private static void consultarCodigo(Scanner scanner, CatalogoProdutos catalogo) {
        String codigo = lerTexto(scanner, "Codigo para consulta: ");
        Optional<Produto> produto = catalogo.buscarPorCodigo(codigo);
        produto.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Nenhum produto encontrado para esse codigo."));
    }

    private static void pesquisar(Scanner scanner, CatalogoProdutos catalogo) {
        String termo = lerTexto(scanner, "Parte do nome ou categoria: ");
        listar(catalogo.buscarPorNomeOuCategoria(termo));
    }

    private static void filtrarEstoque(Scanner scanner, CatalogoProdutos catalogo) {
        int limite = lerInteiro(scanner, "Mostrar produtos com quantidade menor que: ");
        listar(catalogo.listarAbaixoDaQuantidade(limite));
    }

    private static void listar(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        produtos.forEach(System.out::println);
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            String entrada = lerTexto(scanner, mensagem).trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException exception) {
                System.out.println("Informe um numero inteiro valido.");
            }
        }
    }

    private static BigDecimal lerDecimal(Scanner scanner, String mensagem) {
        while (true) {
            String entrada = lerTexto(scanner, mensagem).trim().replace(',', '.');
            try {
                return new BigDecimal(entrada);
            } catch (NumberFormatException exception) {
                System.out.println("Informe um preco valido (ex.: 19,90).");
            }
        }
    }
}
