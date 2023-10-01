import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.PreparedStatement;

public class ShoppingCart {
    List<Item> items;
    Scanner sc;
    private int originalstock;

    public int getOriginalstock() {
        return originalstock;
    }

    public void setOriginalstock(int originalstock) {
        this.originalstock = originalstock;
    }

    public ShoppingCart() {
        items = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=============Welcome to shopiFy=============");
        while (true) {

            System.out.println("1:Availble Items \n2:add item\n3:view a cart\n4:exit\nenter your choice:");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    SelectDb obs = new SelectDb();
                    obs.display();
                    break;
                case 2:
                    do {
                        addToCart();
                        System.out.println("Do you want to add another item? (y/n):");
                        sc.nextLine(); // Consume the newline character
                        String input = sc.nextLine();

                        if (!input.equalsIgnoreCase("y")) {
                            break;
                        }

                    } while (true);
                    break;
                case 3:
                    viewCart();
                    displayBill(items);
                    break;
                case 4:
                    sc.close();
                    System.out.println("thank you for using app");
                    return;
                default:
                    System.out.println("invalied number");


            }
        }
    }


    public void addToCart() {

        System.out.println("enter name");

        String name = sc.nextLine();
        System.out.println("Quantity");
        int stockQuantity = sc.nextInt();
        Item item = new Item(name, stockQuantity);
        items.add(item);


    }

    public void displayBill(List<Item> items) {
        double totalBill = 0;
        for (Item item : items) {
          //  System.out.println(" name: " + item.getName() + " quantity: " + item.getStockQuantity());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");

                // Use a PreparedStatement to safely insert parameters into the SQL query
                String sql = "SELECT price,gst FROM products WHERE product=?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, item.getName()); // Replace "Product_Name" with the actual product name you're looking for

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    double price = rs.getDouble("price");
                    float gst = rs.getFloat("gst");
                    //  setP_gst(p_gst);
                    // setP_price(p_price);
                    double totalPrice ;
                    totalPrice = price * (1+gst/100) * item.getStockQuantity();

                     System.out.print(" Product Name : " + item.getName() +"    Product Quantity "  + item.getStockQuantity()+" Total Price "+totalPrice+"\n"+" "+"\n");

                    totalBill +=  totalPrice;

                }

                // Close the resources
                rs.close();
                preparedStatement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("Final Bill:- "+totalBill);
        System.out.println("===========  ===============");


    }
    public void viewCart() {



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");

            String updateSql = "UPDATE products SET stock = ? WHERE product = ?";
            PreparedStatement updateStatement = con.prepareStatement(updateSql);

            for (Item item : items) {
                String itemName = item.getName();
                int cartQuantity = item.getStockQuantity();

                String selectSql = "SELECT stock FROM products WHERE product=?";
                PreparedStatement selectStatement = con.prepareStatement(selectSql);
                selectStatement.setString(1, itemName);
                ResultSet rs = selectStatement.executeQuery();

                if (rs.next()) {
                    int originalStock = rs.getInt("stock");
                    int newStock = originalStock - cartQuantity;

                    updateStatement.setInt(1, newStock);
                    updateStatement.setString(2, itemName);
                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("");
                    } else {
                        System.out.println("");
                    }
                }

                rs.close();
                selectStatement.close();
            }

            updateStatement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
//    public void viewCart() {
//        // Create a map to store the total quantity of each item in the cart
//
//
//        Map<String, Integer> cartQuantity = new HashMap<>();
//
//        // Calculate the total quantity of each unique item in the cart
//        for (Item item : items) {
//            String itemName = item.getName();
//            int stockQuantity = item.getStockQuantity();
//            cartQuantity.put(itemName, cartQuantity.getOrDefault(itemName, 0) + stockQuantity);
//        }
//
//        // Update the stock for each unique item in the cart
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");
//
//            // Use a PreparedStatement to update the stock for each item in the cart
//            String updateSql = "UPDATE products SET stock = ? WHERE product = ?";
//            PreparedStatement updateStatement = con.prepareStatement(updateSql);
//
//            for (Map.Entry<String, Integer> entry : cartQuantity.entrySet()) {
//                String itemName = entry.getKey();
//                int cartQuantityTotal = entry.getValue();
//
//                // Retrieve the original stock from the database
//                String selectSql = "SELECT stock FROM products WHERE product=?";
//                PreparedStatement selectStatement = con.prepareStatement(selectSql);
//                selectStatement.setString(1, itemName);
//                ResultSet rs = selectStatement.executeQuery();
//
//                if (rs.next()) {
//                    int originalStock = rs.getInt("stock");
//                    int newStock = originalStock - cartQuantityTotal;
//
//                    // Update the stock in the database
//                    updateStatement.setInt(1, newStock);
//                    updateStatement.setString(2, itemName);
//                    int rowsUpdated = updateStatement.executeUpdate();
//
//                    if (rowsUpdated > 0) {
//                        System.out.println("Stock for " + itemName + " updated successfully.");
//                    } else {
//                        System.out.println("Stock update for " + itemName + " failed. Product not found.");
//                    }
//                }
//
//                // Close the resources
//                rs.close();
//                selectStatement.close();
//            }
//
//            // Close the resources
//            updateStatement.close();
//            con.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}

//    public void viewCart(){
//        for (Item item : items){
//            System.out.println(" name: "+item.getName()+" quantity: "+item.getStockQuantity());
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");
//
//                // Use a PreparedStatement to safely insert parameters into the SQL query
//                String sql = "SELECT stock FROM products WHERE product=?";
//                PreparedStatement preparedStatement = con.prepareStatement(sql);
//                preparedStatement.setString(1, "suger"); // Replace "Product_Name" with the actual product name you're looking for
//
//                ResultSet rs = preparedStatement.executeQuery();
//
//                while (rs.next()) {
//                    int originalstock1 = rs.getInt("stock");
//                   // System.out.println("Stock: " + originalstock);
//                    setOriginalstock(originalstock1);
//                }
//
//                // Close the resources
//                rs.close();
//                preparedStatement.close();
//                con.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");
//
//                // Update the stock of the product
//                String updateSql = "UPDATE products SET stock = ? WHERE product = ?";
//                PreparedStatement updateStatement = con.prepareStatement(updateSql);
//                updateStatement.setInt(1, getOriginalstock()-item.getStockQuantity()); // Set the new stock value
//                updateStatement.setString(2, item.getName()); // Use the item's name for the query
//
//                int rowsUpdated = updateStatement.executeUpdate();
//                if (rowsUpdated > 0) {
//                    System.out.println("Stock updated successfully.");
//                } else {
//                    System.out.println("Stock update failed. Product not found.");
//                }
//
//                updateStatement.close();
//                con.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
  ///  }



