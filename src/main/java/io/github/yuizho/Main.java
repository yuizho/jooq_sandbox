package io.github.yuizho;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static io.github.yuizho.jooq.tables.Division.DIVISION;
import static io.github.yuizho.jooq.tables.Product.PRODUCT;

public class Main {
    public static void main(String... args) throws SQLException {
        String userName = "test";
        String password = "password";
        String url = "jdbc:mysql://localhost:3306/test";

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            System.out.println("------------------ fetch ----------------------");
            fetchProduct(create);

            System.out.println("------------------ fetch after delete-insert ----------------------");
            int deleted = create.delete(PRODUCT)
                    .where(PRODUCT.NAME.eq("with jooq"))
                    .execute();
            System.out.printf("%d records were deleted\n", deleted);
            int registered = create.insertInto(PRODUCT)
                    .set(PRODUCT.NAME, "with jooq")
                    .set(PRODUCT.CREATED, Timestamp.valueOf(LocalDateTime.now()))
                    .set(PRODUCT.DIVISION, UInteger.valueOf(1))
                    .execute();
            System.out.printf("%d records were registered\n", registered);
            fetchProduct(create);

            System.out.println("------------------ fetch with joininig ----------------------");
            fetchWithJoining(create);


        }
    }

    private static void fetchWithJoining(DSLContext create) {
        Result<Record2<String, String>> results2 =
                create.select(PRODUCT.NAME, DIVISION.NAME)
                        .from(PRODUCT)
                        .join(DIVISION).on(DIVISION.ID.eq(PRODUCT.DIVISION))
                        .fetch();
        results2.stream().forEach(result -> {
            String productName = result.getValue(PRODUCT.NAME);
            String divisionName = result.getValue(DIVISION.NAME);
            System.out.println(
                    "productName: " + productName
                    + ", divisionName: " + divisionName
            );
        });
    }

    private static void fetchProduct(DSLContext create) {
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
