package io.github.yuizho;

import io.github.yuizho.jooq.Tables;
import io.github.yuizho.jooq.tables.Product;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static io.github.yuizho.jooq.tables.Product.PRODUCT;

public class Main {
    public static void main(String... args) throws SQLException {
        String userName = "test";
        String password = "password";
        String url = "jdbc:mysql://localhost:3306/test";

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> results = create.select().from(PRODUCT).fetch();
            for (Record result : results) {
                UInteger id = result.getValue(PRODUCT.ID);
                String name = result.getValue(PRODUCT.NAME);
                Date created = result.getValue(PRODUCT.CREATED);
                System.out.println(
                        "ID: " + id
                        + ", NAME: " + name
                        + ", CREATED: " + created
                );
            }
        }
    }
}
