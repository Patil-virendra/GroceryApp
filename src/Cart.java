//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//
//public class Cart {
//    private List<Product> cartItems;
//    private Scanner sc;
//    private SelectDb obs;
//
//    public Cart() {
//        cartItems = new ArrayList<>();
//        sc = new Scanner(System.in);
//        obs = new SelectDb();
//    }
//
//    public void addToCart(Product product, int quantity) {
//        if (product.getStock() >= quantity) {
//            cartItems.add(product);
//            System.out.println(quantity + " " + product.getName() + " added to the cart.");
//        } else {
//            System.out.println("Insufficient stock for " + product.getName());
//        }
//    }
//
//    public void run() {
//        boolean running = true;
//
//        while (running) {
//            System.out.println("=================Welcome to SHOP====================");
//            System.out.println("1.Show Items");
//            System.out.println("2.Add Items to Cart");
//            System.out.println("3.Display Cart");
//            System.out.println("4.Display Bill");
//            System.out.println("5.Exit from shopping");
//            System.out.println("Enter your choice:");
//
//            int choice = sc.nextInt();
//            List<Product> products = new ArrayList<>(); // Initialize outside the switch
//
//            switch (choice) {
//                case 1:
//                    products = obs.getAllProducts();
//                    System.out.println("Available Products:");
//                    for (int i = 0; i < products.size(); i++) {
//                        Product product = products.get(i);
//                        System.out.println((i + 1) + ". " + product.getName() + " - Price: " + product.getPrice() + ", Stock: " + product.getStock());
//                    }
//                    break;
//
//                case 2:
//                    System.out.println("Enter the product number to add to cart:");
//                    int productNumber = sc.nextInt();
//
//                     if (productNumber >= products.size()) {
//
//        Product selectedProduct = products.get(productNumber - 1);
//                         System.out.println(selectedProduct);
//        System.out.println("Enter the quantity to add to cart:");
//        int quantity = sc.nextInt();
//        addToCart(selectedProduct, quantity);
//                    } else {
//                        System.out.println("Invalid product number.");
//                    }
//                    break;
//
//                case 3:
//                    // Display the cart contents
//                    System.out.println("Cart Contents:");
//                    for (Product item : cartItems) {
//                        System.out.println(item.getName() + " - Quantity: " + countOccurrences(cartItems, item));
//                    }
//                    break;
//
//                case 4:
//                    // Calculate and display the total bill
//                    int totalBill = 0;
//                    for (Product item : cartItems) {
//                        totalBill += item.getPrice() * countOccurrences(cartItems, item);
//                    }
//                    System.out.println("Total Bill: $" + totalBill);
//                    break;
//
//                case 5:
//                    running = false;
//                    System.out.println("Exiting the application.");
//                    break;
//
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//                    break;
//            }
//        }
//    }
//
//    private int countOccurrences(List<Product> list, Product product) {
//        int count = 0;
//        for (Product item : list) {
//            if (item.equals(product)) {
//                count++;
//            }
//        }
//        return count;
//    }
//
//    public static void main(String[] args) {
//        Cart cart = new Cart();
//        cart.run();
//    }
//}