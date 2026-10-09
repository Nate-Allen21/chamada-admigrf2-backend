# ADMIG RF II — Backend

API REST do sistema de chamada da Escola Dominical ADMIG RF II.

## Executar

```bash
mvn spring-boot:run
```

O backend utiliza PostgreSQL e fica disponível em `http://localhost:8080`.
Configure `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`,
`ADMIN_EMAIL` e `ADMIN_PASSWORD` por variáveis de ambiente.
