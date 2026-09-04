FROM ubuntu:latest
LABEL authors="surya"

ENTRYPOINT ["top", "-b"]




docker run -d --name pgvector-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=vectordb -p 5432:5432 pgvector/pgvector:pg16