# ADMIG RF II — Backend

API REST do sistema de chamada da Escola Dominical ADMIG RF II.

## Executar

```bash
mvn spring-boot:run
```

O backend utiliza PostgreSQL e fica disponível em `http://localhost:8080`.
Configure `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`,
`ADMIN_EMAIL` e `ADMIN_PASSWORD` por variáveis de ambiente.

## Deploy no Render com Supabase

O arquivo `render.yaml` já define o serviço Docker, a porta, o health check e o
disco persistente para fotos. No Render, informe:

- `DB_HOST`, `DB_USER` e `DB_PASSWORD` obtidos em Supabase;
- `DB_PORT=5432`, `DB_NAME=postgres` e `DB_SSLMODE=require`;
- `CORS_ORIGINS` com a URL pública da Vercel;
- `ADMIN_EMAIL` e `ADMIN_PASSWORD` para o primeiro administrador.

Use a conexão PostgreSQL do Supabase adequada para um backend persistente e
adicione `sslmode=require`. Nunca coloque a senha do banco ou o JWT no Git.
