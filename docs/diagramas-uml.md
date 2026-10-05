# Diagramas UML

Os diagramas abaixo usam Mermaid e podem ser visualizados no GitHub.

## Diagrama estrutural: pacotes e dependencias

```mermaid
flowchart LR
    subgraph Aplicacao
        Main --> CatalogoProdutos
    end
    subgraph Dominio
        CatalogoProdutos --> Produto
    end
    Main --> Produto
    CatalogoProdutos -. "armazena em array" .-> Array["Produto[]"]
```

## Diagrama de classes

```mermaid
classDiagram
    class Produto {
        -String codigo
        -String nome
        -String categoria
        -BigDecimal preco
        -int quantidade
        +Produto(codigo, nome, categoria, preco, quantidade)
        +getCodigo() String
        +getNome() String
        +getCategoria() String
        +getPreco() BigDecimal
        +getQuantidade() int
        +getValorEmEstoque() BigDecimal
    }
    class CatalogoProdutos {
        -Produto[] produtos
        -int quantidadeCadastrada
        +CatalogoProdutos(capacidade)
        +inserir(produto) void
        +buscarPorCodigo(codigo) Optional~Produto~
        +listarTodos() List~Produto~
        +buscarPorNomeOuCategoria(consulta) List~Produto~
        +listarAbaixoDaQuantidade(limite) List~Produto~
        +calcularValorTotalEmEstoque() BigDecimal
    }
    class Main {
        +main(args) void
    }
    CatalogoProdutos "1" o--> "0..*" Produto : array de capacidade fixa
    Main ..> CatalogoProdutos : utiliza
    Main ..> Produto : cria
```

## Diagrama comportamental: casos de uso

```mermaid
flowchart LR
    usuario[Usuário]
    subgraph sistema["Catálogo de Produtos"]
        uc1((Cadastrar produto))
        uc2((Listar produtos))
        uc3((Consultar por código))
        uc4((Pesquisar nome ou categoria))
        uc5((Filtrar estoque baixo))
        uc6((Calcular valor em estoque))
    end
    usuario --- uc1
    usuario --- uc2
    usuario --- uc3
    usuario --- uc4
    usuario --- uc5
    usuario --- uc6
```

## Diagrama comportamental: sequência de cadastro

```mermaid
sequenceDiagram
    actor Usuario
    participant Menu as Main
    participant Catalogo as CatalogoProdutos
    participant Produto
    Usuario->>Menu: informa dados do produto
    Menu->>Produto: cria com construtor
    Produto->>Produto: valida e normaliza campos
    Produto-->>Menu: produto válido
    Menu->>Catalogo: inserir(produto)
    Catalogo->>Catalogo: verifica capacidade e código duplicado
    alt cadastro permitido
        Catalogo->>Catalogo: guarda no próximo índice do array
        Catalogo-->>Menu: sucesso
        Menu-->>Usuario: Produto cadastrado
    else inválido, duplicado ou catálogo cheio
        Catalogo-->>Menu: lança erro explícito
        Menu-->>Usuario: exibe mensagem de erro
    end
```

## Diagrama comportamental: atividade de pesquisa

```mermaid
flowchart TD
    A([Início]) --> B[Receber termo]
    B --> C{Termo vazio?}
    C -- Sim --> D[Exibir nenhum produto encontrado]
    C -- Não --> E[Remover espaços externos, acentos e diferenças de caixa]
    E --> F[Percorrer produtos cadastrados no array]
    F --> G{Nome ou categoria contém o termo?}
    G -- Sim --> H[Adicionar produto aos resultados]
    G -- Não --> I{Há mais produtos?}
    H --> I
    I -- Sim --> F
    I -- Não --> J[Exibir resultados ou mensagem de lista vazia]
    D --> K([Fim])
    J --> K
```
