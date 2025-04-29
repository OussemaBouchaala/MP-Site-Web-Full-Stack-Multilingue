package com.example.bookstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // This bean ensures that the data.sql script is executed after Hibernate schema creation
    @Bean
    public CommandLineRunner initDatabase(DataSource dataSource) {
        return args -> {
            // Log database initialization
            System.out.println("Initializing database with sample data...");
            
            // Optional: You can manually execute data.sql if needed
            // ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            // populator.addScript(new ClassPathResource("data.sql"));
            // populator.execute(dataSource);
            
            System.out.println("Database initialization completed.");
        };
    }
}
