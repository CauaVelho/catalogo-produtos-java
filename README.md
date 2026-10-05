# Catálogo de Produtos em Java

Aplicação de console para cadastrar, consultar e apresentar produtos usando construtores, manipulação de `String` e um array de capacidade fixa. Requer **JDK 17 ou superior** e não usa bibliotecas externas.

## Executar

No PowerShell, a partir da pasta raiz do projeto:

```powershell
javac -encoding UTF-8 -d out src\main\java\br\com\cauavelho\catalogo\Produto.java src\main\java\br\com\cauavelho\catalogo\CatalogoProdutos.java src\main\java\br\com\cauavelho\catalogo\Main.java
java -cp out br.com.cauavelho.catalogo.Main
```

O programa inicia com capacidade para 100 produtos. O menu permite cadastrar, listar, consultar por código, pesquisar nome ou categoria, filtrar estoque abaixo de um limite e calcular o valor total do estoque. Preços aceitam ponto ou vírgula como separador decimal.

## Testes

```powershell
javac -encoding UTF-8 -d out src\main\java\br\com\cauavelho\catalogo\Produto.java src\main\java\br\com\cauavelho\catalogo\CatalogoProdutos.java src\test\java\br\com\cauavelho\catalogo\CatalogoProdutosTest.java
java -ea -cp out br.com.cauavelho.catalogo.CatalogoProdutosTest
```

Os testes exercitam catálogos com capacidades 1, 10 e 1.000, além de validações, pesquisa parcial sem distinção de acentos, cálculo decimal, filtros e erros de limite. O relatório completo está em [docs/relatorio-funcionalidades-e-testes.md](docs/relatorio-funcionalidades-e-testes.md).

## UML

Diagramas estrutural, de classes, de casos de uso, sequência e atividade estão em [docs/diagramas-uml.md](docs/diagramas-uml.md).

## Publicar no GitHub

1. Crie um repositório **vazio**, sem README, licença ou `.gitignore`, na conta [CauaVelho](https://github.com/CauaVelho). Use, por exemplo, o nome `catalogo-produtos-java`.
2. No PowerShell, vá para a pasta do projeto e execute:

```powershell
Set-Location "C:\Users\Yasuzo\OneDrive\Documentos\java"
git init
git add README.md docs src
git commit -m "Adiciona catálogo de produtos em Java"
git branch -M main
git remote add origin https://github.com/CauaVelho/catalogo-produtos-java.git
git push -u origin main
```

Se o repositório tiver outro nome, substitua `catalogo-produtos-java` na URL. Se `origin` já existir, confira `git remote -v` e atualize-o com `git remote set-url origin https://github.com/CauaVelho/catalogo-produtos-java.git` antes do `push`. O GitHub pode solicitar autenticação pelo navegador ou um token pessoal; não inclua credenciais nos arquivos do projeto.
