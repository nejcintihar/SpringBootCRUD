FROM postgres:latest

ENV POSTGRES_DB=productdb \
    POSTGRES_USER=YOUR_USER \
    POSTGRES_PASSWORD=YOUR_PASSWORD

COPY . .

VOLUME ["/var/lib/postgresql/data"]

EXPOSE 5432

COPY docker-entrypoint-initdb.d/ /docker-entrypoint-initdb.d/

CMD ["postgres"]