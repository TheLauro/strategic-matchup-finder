# ⚔️ Strategic Matchup Finder

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)
![Vanilla JS](https://img.shields.io/badge/JavaScript-Vanilla-yellow.svg)

*Leia em outro idioma: [🇺🇸 English](README.md)*

Uma aplicação Full-Stack projetada para analisar e exibir matchups estratégicas do jogo League of Legends. A plataforma permite que os jogadores verifiquem taxas de vitória contra oponentes específicos com base em dados reais e dinâmicos, cruzando essas estatísticas com a sua própria *Champion Pool* (lista de campeões que o usuário domina).

## 🎯 O Propósito: De Console App ao Full-Stack
Este projeto marca a minha transição técnica oficial. Antes de iniciá-lo, minha experiência se resumia a lógicas rodando inteiramente em aplicações de console. 

O **Strategic Matchup Finder** nasceu com o propósito estrito de me desafiar a sair da zona de conforto. Em um período de aproximadamente 4 meses, construí este ecossistema do zero, aprendendo e aplicando na prática, pela primeira vez:
* Como desenhar uma **Arquitetura de Camadas** (Controller, Service, Repository).
* Como o **Spring Boot** gerencia injeção de dependências e expõe rotas HTTP.
* Como modelar e integrar um banco de dados relacional (**PostgreSQL**) usando ORM (Hibernate).
* Como construir e manipular o DOM no **Frontend** (HTML/CSS/JS) e fazê-lo consumir uma API REST de forma assíncrona.

---

## 🛠️ Tecnologias e Ferramentas

### Backend (Core)
* **Java 21:** Utilização de recursos modernos como `Records` para a criação de DTOs imutáveis, garantindo um tráfego de dados seguro e limpo entre as camadas.
* **Spring Boot (Web, Data JPA):** Criação de uma API RESTful robusta.
* **PostgreSQL & Hibernate:** Modelagem de banco de dados relacional e persistência de dados.
* **JSoup (Web Scraping):** Implementação de um pipeline de automação para extração de dados reais.
* **Lombok:** Redução de boilerplate code para entidades e injeção de dependências.

### Frontend
* **HTML5, CSS3 & Vanilla JavaScript:** Construção de uma interface responsiva e dinâmica sem uso de frameworks, demonstrando domínio absoluto sobre eventos e requisições assíncronas (`Fetch API`, `Promises`).

---

## 🚀 Destaques Técnicos (Para Recrutadores)

Este projeto foi construído focando em boas práticas, performance e resiliência de dados:

### 1. Automação e Extração de Dados Reais (Web Scraping)
O sistema não depende de dados inseridos manualmente ou de APIs estáticas. O `ScrapperService` atua como um robô que raspa a plataforma *u.gg* dinamicamente.
* **Rate Limiting Consciente:** Implementação de controle de requisições (`Thread.sleep`) para respeitar o servidor alvo e evitar bloqueios.
* **Tratamento de Dados:** Parseamento complexo de HTML, tratamento de anomalias em strings e tipagem segura antes da persistência no banco.

### 2. Otimização de Banco de Dados e Queries (Evitando o Problema N+1)
Para garantir alta performance ao buscar a lista de matchups, a comunicação com o banco de dados foi otimizada usando **Custom Queries em JPQL**.
* O uso de `JOIN FETCH` no repositório assegura que o Campeão, o Oponente e a Matchup sejam trazidos à memória em uma única viagem ao banco (`Single Query`), evitando o colapso de performance comum do Hibernate (Problema N+1).
* Utilização de `@UniqueConstraint` na modelagem da base de dados para garantir integridade referencial a nível de SGBD (amarrando Herói + Inimigo + Rota), prevenindo dados duplicados em execuções concorrentes do scraper.

### 3. Gerenciamento de Transações (ACID)
As rotinas de inserção em massa (como a atualização de centenas de matchups simultâneas) são envelopadas pela anotação `@Transactional`, garantindo a atomicidade da operação: ou o banco atualiza todos os dados com sucesso, ou realiza o *Rollback* completo em caso de falha de rede ou exceção, protegendo o sistema contra dados corrompidos.

### 4. Arquitetura Desacoplada e Imutabilidade
* **Isolamento de Domínio:** Entidades do banco de dados (`@Entity`) nunca são expostas ao Frontend. O tráfego ocorre estritamente via `DTOs` imutáveis.
* **CORS Configuration:** Backend configurado nativamente para lidar com requisições cross-origin, permitindo escalabilidade para implantação em domínios separados.

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
* Java 21+
* Maven
* PostgreSQL rodando localmente (ou via Docker)

### Configuração do Banco de Dados
O projeto já conta com um `docker-compose.yml` para facilitar a subida do banco.
```bash
# Subir o banco de dados via Docker
docker-compose up -d
```

_Caso rode o Postgres localmente sem Docker, certifique-se de criar um banco chamado `lolproject` e atualizar o `application.properties` com as suas credenciais._

### Rodando o Backend

Bash

```
cd backend
./mvnw spring-boot:run
```

**Inicialização do Banco (Primeira Execução):** Abra outro terminal e execute os seguintes comandos `curl` para popular os dados e criar o usuário de teste:
   * Buscar matchups (Demora ~40 min): `curl -X POST http://localhost:8080/matchups/update`
   * Atualizar rotas comuns: `curl -X PUT http://localhost:8080/champions`
   * Criar usuário: `curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"name\": \"teste\", \"email\": \"teste@gmail.com\"}"`

_Nota: Ao subir a aplicação pela primeira vez, o `DatabaseSeeder` irá invocar o Scrapper automaticamente para popular a tabela de campeões._

### Rodando o Frontend

Basta abrir o arquivo `/frontend/index.html` em qualquer navegador moderno, ou utilizar uma extensão como o _Live Server_ do VS Code.


**_Para rodar a versão final compilada sem precisar baixar o código-fonte, acesse a aba de Releases e siga as instruções lá contidas._**

---

## 📈 Próximos Passos (Roadmap)

- [ ] **Testes Unitários e de Integração:** Implementar cobertura de testes no backend utilizando ferramentas clássicas do ecossistema (como **JUnit** e **Mockito**) para garantir a estabilidade das regras de negócio e evitar regressões.

- [ ] **Agendamento de Tarefas (Cron Jobs):** Implementar a anotação `@Scheduled` do Spring para que o `ScrapperService` rode automaticamente durante a madrugada, mantendo a base de dados sempre atualizada sem intervenção manual.

- [ ] **Otimização de Algoritmos no Frontend:** Refatoração das lógicas de busca (`find/filter`) utilizando HashMaps (Objetos/Maps em JS) para reduzir a complexidade de tempo de $O(n)$ para $O(1)$.

- [ ] **Sistema de Autenticação:** Implementar Spring Security e JWT para gerenciar o acesso real de usuários e substituir o ID fixo (hardcoded) atual.

- [ ] **Dockerização Completa:** Criar as imagens Docker para o Backend e o Frontend, unificando a infraestrutura.

- [ ] **Deploy em Nuvem (CI/CD):** Realizar o deploy da aplicação em serviços de nuvem (como AWS, Render ou Railway) utilizando as imagens geradas na etapa de dockerização.

---

_Desenvolvido com curiosidade, lógica e muita sede de ser campeão._ 🏆