# Relatório de funcionalidades, validações e testes

## Funcionalidades implementadas

- Cadastro de produtos com código, nome, categoria, preço e quantidade.
- Busca por código sem diferenciar maiúsculas de minúsculas.
- Busca parcial por nome ou categoria, sem diferenciar caixa ou acentos.
- Listagem completa e filtro por quantidade estritamente abaixo de um limite.
- Cálculo do valor total do estoque como preço multiplicado pela quantidade.
- Menu interativo de console com mensagens para entradas inválidas, códigos repetidos e catálogo cheio.
- Catálogo em array com capacidade configurável; a aplicação de console usa capacidade inicial de 100 itens.

## Bugs e validações tratados

Não foram identificados bugs pendentes nos cenários exercitados. Foram prevenidos ou tratados:

| Cenário | Comportamento esperado |
|---|---|
| Código, nome ou categoria em branco | Rejeitar o produto com mensagem explícita |
| Preço ou quantidade negativos | Rejeitar a entrada |
| Preço não numérico ou decimal não finito | Rejeitar a entrada |
| Código já cadastrado (sem diferenciar caixa) | Impedir a duplicação |
| Inserção acima da capacidade | Informar que o catálogo está cheio |
| Pesquisa sem correspondência | Retornar lista vazia, sem falhar |
| Tentativa de alterar a listagem retornada | Impedir a alteração da coleção do catálogo |
| Quantidade ou capacidade inválida | Rejeitar o valor |

## Testes executados

A classe de teste verifica os construtores e validações, inserção, consulta, duplicidade, pesquisa por nome/categoria ignorando acentos e caixa, filtro, precisão decimal do total em estoque, imutabilidade da listagem e limites de capacidade.

Foram exercitadas capacidades de **1, 10 e 1.000 produtos**, incluindo a tentativa de exceder cada capacidade. Para executar:

```powershell
javac -encoding UTF-8 -d out src\main\java\br\com\cauavelho\catalogo\Produto.java src\main\java\br\com\cauavelho\catalogo\CatalogoProdutos.java src\test\java\br\com\cauavelho\catalogo\CatalogoProdutosTest.java
java -ea -cp out br.com.cauavelho.catalogo.CatalogoProdutosTest
```

## Vantagens e limitações de arrays

**Vantagens:** armazenamento simples e contíguo, acesso por índice em O(1), estrutura adequada para exercitar arrays e pouca sobrecarga de memória por coleção.

**Limitações:** a capacidade não cresce automaticamente; é necessário escolher o tamanho e tratar catálogo cheio. A busca por código, nome ou categoria percorre os itens em O(n), e remover elementos ou aumentar a capacidade exige deslocamento ou cópia. Em uma aplicação de produção com volume variável, `ArrayList` (crescimento dinâmico) ou um banco de dados (persistência e consultas) seriam opções mais apropriadas.
