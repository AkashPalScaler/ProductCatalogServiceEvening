package com.scaler.productcatalogserviceevening;

import com.scaler.productcatalogserviceevening.models.Product;

import java.math.BigDecimal;
import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) throws SQLException {
//        System.out.println(0.1);
//        System.out.println(0.2);
//        System.out.println(0.3);
//        System.out.println(0.1 + 0.2);
//        System.out.println(new BigDecimal(0.1));
//        System.out.println(new BigDecimal(0.2));
//        System.out.println(new BigDecimal(0.3));
//        System.out.println(new BigDecimal(0.1 + 0.2));

//        ----------------------------------------------

        String url = "jdbc:mysql://localhost:3306/productcatalogserviceevening";
        try (
            Connection con = DriverManager.getConnection(url, "akashpal", System.getenv("DB_PASSWORD"));
            PreparedStatement ps = con.prepareStatement("SELECT id, name, price FROM demo_product WHERE id = ?")
        ) {
            ps.setLong(1, 2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
//                product.setPrice(rs.getDouble("price"));
                System.out.println(product.getName() + " costs " + product.getPrice());
            }
        }
    }
}
// 1/3 - > 0.34 * 3 -> 1
// 2^1, 2^0, 1/2, 1/4, 1/8
// 1/10 -> 0.001100110011
// 0.1000000000237483432
// 0.20000000002387236497
// 0.300000000000117239793

// try-with-resource - try ( resources ) { scope }

// try{
// }catch{
// }finally{
//        resource.close()
// }

// "SELECT id, name, price FROM demo_product WHERE id = ?")
// String id = "2; DROP TABLES;"

// SELECT id, name, price FROM demo_product WHERE id = 2; DROP TABLES;

// SELECT id, name, price FROM demo_product WHERE id = "2; DROP TABLES;"