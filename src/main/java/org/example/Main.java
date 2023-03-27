package org.example;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        try(Connection conn = ConnectionFactory.getConnection();) {
            DAOImplementation<Product> daoProduct = new DAOImplementation<>(conn, "ProductsTable");
            DAOImplementation<Client> daoClient = new DAOImplementation<>(conn, "ClientsTable");
            DAOImplementation<Order> daoOrder = new DAOImplementation<>(conn, "OrdersTable");
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS " + daoProduct.getTABLE());
                    st.execute("DROP TABLE IF EXISTS " + daoClient.getTABLE());
                    st.execute("DROP TABLE IF EXISTS " + daoOrder.getTABLE());

                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            //Creating tables of Entities, and the entities to Insert.
            daoProduct.createTable(Product.class);
            Product product1 = new Product("product1", 11.1);
            Product product2 = new Product("product2", 22.2);
            Product product3 = new Product("product3", 33.3);
            daoProduct.add(product1);
            daoProduct.add(product2);
            daoProduct.add(product3);

            daoClient.createTable(Client.class);
            Client client1 = new Client("client1", "cl1@gmail.com");
            Client client2 = new Client("client2", "cl2@gmail.com");
            Client client3 = new Client("client3", "cl3@gmail.com");
            daoClient.add(client1);
            daoClient.add(client2);
            daoClient.add(client3);

            daoOrder.createTable(Order.class);
            Order order1 = new Order(991, 2, product1, 1, 14);
            Order order2 = new Order(992, 2, product2, 2,6);
            Order order3 = new Order(993, 3, product3, 3,13);
            daoOrder.add(order1);
            daoOrder.add(order2);
            daoOrder.add(order3);

            //show the total pay of the chosen client by his id.
            daoOrder.showOrderTotalPayByClientId(2);

        } catch (Exception e) {
            System.out.println("***** You have problems with connection *****");
            e.printStackTrace();
        }
    }
}
