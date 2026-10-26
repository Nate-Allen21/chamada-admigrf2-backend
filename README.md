# ADMIG RF II — Backend

API REST do sistema de chamada da Escola Dominical ADMIG RF II.

## Executar localmente com Docker

```bash
docker compose up --build
```

O Compose inicia o PostgreSQL e a API em `http://localhost:8080`; os dados ficam
em volumes Docker. Para executar o backend fora do Docker, inicie um PostgreSQL
com as mesmas credenciais e use `mvn spring-boot:run`.

## Deploy da API no Render com banco Supabase

O `render.yaml` configura a API Docker, health check e disco persistente para
uploads. Crie o projeto/banco no Supabase e copie as credenciais PostgreSQL do
**Session pooler** (recomendado quando o host do Render não oferece IPv6):

- `DB_HOST`: host exibido pelo Supabase em Connect > Session pooler;
- `DB_PORT`: porta exibida pelo Supabase, normalmente `5432` no Session pooler;
- `DB_NAME`: `postgres`;
- `DB_USER`: usuário exibido pelo Supabase, frequentemente `postgres.<project-ref>`;
- `DB_PASSWORD`: senha do banco Supabase;
- `DB_SSLMODE`: `require`;
- `JWT_SECRET`: segredo aleatório longo e privado (pelo menos 32 caracteres);
- `CORS_ORIGINS`: domínio exato da Vercel, por exemplo `https://seu-app.vercel.app`;
- `ADMIN_EMAIL` e `ADMIN_PASSWORD`: credenciais iniciais fortes do administrador;
- `UPLOAD_DIR`: `/var/data/uploads` (já definido no Blueprint; mantenha o disco Render).

O `render.yaml` já cria as chaves acima como variáveis para preencher ou gera o
`JWT_SECRET`. Se usar `SPRING_DATASOURCE_URL`, ela substitui host/porta/nome e
deve incluir `?sslmode=require`; não é necessário configurar essa alternativa
quando preencher `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER` e `DB_PASSWORD`.
O Hibernate cria/atualiza tabelas na inicialização (`ddl-auto: update`). Nunca
commite segredos ou use as senhas locais em produção.

Fotos são guardadas no disco persistente do Render. O disco do plano gratuito
pode não estar disponível; sem volume persistente, uploads podem sumir em novo
deploy/restart. Para produção mais resiliente, migre fotos para Supabase Storage.
