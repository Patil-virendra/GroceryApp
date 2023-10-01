import java.sql.*;
import java.util.*;

public class SelectDb {

    public void display() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from products");
            while (rs.next()) {
                String product = rs.getString("product");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                float gst = rs.getFloat("gst");
                System.out.print(product+" ");
                System.out.print(price+" ");
                System.out.print(stock+" ");
                System.out.println(gst);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}