# Polling and Voting API

## Tema do Projeto e Justificativa

O projeto "Polling and Voting" é uma API REST construída em Java com Spring Boot, destinada a gerenciar enquetes, opções de voto e os votos dos usuários. A ideia é oferecer uma solução prática e confiável para criar, votar e apurar resultados de enquetes online, tornando a integração com sistemas web e mobile mais fácil. A razão para desenvolver esse projeto vem da necessidade comum de sistemas de votação em contextos acadêmicos, em eventos, em pesquisas de opinião, e para decisões rápidas em grupos, onde a transparência e a facilidade de uso são super importantes.

## Diagrama das Entidades e Relacionamentos

![Diagrama ER](er.png)

## Lista de Endpoints

- **Surveys**
  - `GET /api/v1/surveys` — Listar todas as pesquisas
  - `GET /api/v1/surveys/{id}` — Detalhar uma pesquisa (com opções)
  - `POST /api/v1/surveys` — Criar uma nova pesquisa
  - `PUT /api/v1/surveys/{id}` — Atualizar uma pesquisa
  - `DELETE /api/v1/surveys/{id}` — Remover uma pesquisa
  - `GET /api/v1/surveys/{id}/results` — Resultado da pesquisa

- **Options**
  - `POST /api/v1/surveys/{surveyId}/options` — Adicionar opção à pesquisa
  - `DELETE /api/v1/options/{id}` — Remover opção

- **Votes**
  - `POST /api/v1/vote` — Registrar voto

## Como rodar e buildar o projeto

Para rodar tudo (aplicação + banco) usando Docker:

```sh
docker-compose up --build
```

A aplicação estará disponível em http://localhost:8080 e o banco em localhost:5432.

Para rodar apenas local (sem Docker), suba o banco com `docker-compose up -d postgres` e rode a aplicação normalmente pelo Maven ou sua IDE.
