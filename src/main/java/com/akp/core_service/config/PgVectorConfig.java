package com.akp.core_service.config;

import com.pgvector.PGvector;
import jakarta.annotation.PostConstruct;
import org.postgresql.PGConnection;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class PgVectorConfig {

    private final DataSource dataSource;

    public PgVectorConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void registerVectorType() {

        try (Connection connection = dataSource.getConnection()) {

            PGConnection pgConnection =
                    connection.unwrap(PGConnection.class);

            pgConnection.addDataType("vector", PGvector.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}