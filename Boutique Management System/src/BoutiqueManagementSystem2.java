import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BoutiqueManagementSystem2 extends JFrame {
    Connection conn;
    JTextArea output = new JTextArea(); // Used in view methods

    public BoutiqueManagementSystem2() {
        setTitle("Boutique Management System");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            Image background = new ImageIcon("E:\\BSCYS-SEM 2\\java project\\BMS\\bms\\src\\Boutique management system.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(560, 400, 200, 50);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 27));
        loginButton.setBackground(new Color(192, 192, 192));
        backgroundPanel.add(loginButton);

        loginButton.addActionListener(e -> showLoginPanel());

        setVisible(true);
    }

   private void showLoginPanel() {
    JFrame loginFrame = new JFrame("Login");
    loginFrame.setSize(1366, 768);
    loginFrame.setLocationRelativeTo(null);
    loginFrame.getContentPane().setBackground(new Color(245, 245, 250));
    loginFrame.setLayout(null);

    // Enhanced title panel
    JPanel title = new JPanel();
    title.setBounds(0, 0, 1366, 120);
    title.setBackground(new Color(255, 182, 193));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

    JLabel titleLabel = new JLabel("Boutique Management System");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
    titleLabel.setForeground(Color.WHITE);
    title.add(titleLabel);

    // Centered login form with better spacing
    JPanel form = new JPanel();
    form.setBounds(433, 180, 500, 450);
    form.setBackground(Color.WHITE);
    form.setLayout(null);
    form.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
        BorderFactory.createEmptyBorder(40, 40, 40, 40)
    ));

    // Login form title
    JLabel formTitle = new JLabel("Sign In", JLabel.CENTER);
    formTitle.setBounds(0, 20, 420, 40);
    formTitle.setFont(new Font("Arial", Font.BOLD, 28));
    formTitle.setForeground(new Color(255, 182, 193));

    // Username section
    JLabel username = new JLabel("Username:");
    username.setBounds(40, 100, 150, 30);
    username.setFont(new Font("Arial", Font.BOLD, 18));
    username.setForeground(new Color(60, 60, 60));

    JTextField enterUN = new JTextField();
    enterUN.setBounds(40, 135, 340, 45);
    enterUN.setFont(new Font("Arial", Font.PLAIN, 16));
    enterUN.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
        BorderFactory.createEmptyBorder(10, 15, 10, 15)
    ));

    // Password section
    JLabel password = new JLabel("Password:");
    password.setBounds(40, 200, 150, 30);
    password.setFont(new Font("Arial", Font.BOLD, 18));
    password.setForeground(new Color(60, 60, 60));

    JPasswordField enterPass = new JPasswordField();
    enterPass.setBounds(40, 235, 340, 45);
    enterPass.setFont(new Font("Arial", Font.PLAIN, 16));
    enterPass.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
        BorderFactory.createEmptyBorder(10, 15, 10, 15)
    ));

    // Enhanced login button
    JButton login = new JButton("LOGIN");
    login.setBounds(120, 320, 180, 50);
    login.setBackground(new Color(255, 182, 193));
    login.setForeground(Color.WHITE);
    login.setFont(new Font("Arial", Font.BOLD, 18));
    login.setBorder(BorderFactory.createEmptyBorder());
    login.setFocusPainted(false);

    // Button hover effect
    login.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            login.setBackground(new Color(255, 200, 210));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            login.setBackground(new Color(255, 182, 193));
        }
    });

    login.addActionListener(e -> {
        String user = enterUN.getText();
        String pass = new String(enterPass.getPassword());

        try {
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();
                SwingUtilities.invokeLater(this::showDashboard);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Wrong username or password!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(loginFrame, "Error: " + ex.getMessage());
        }
    });

    // Add components to form
    form.add(formTitle);
    form.add(username);
    form.add(enterUN);
    form.add(password);
    form.add(enterPass);
    form.add(login);

    // Add main panels to frame
    loginFrame.add(title);
    loginFrame.add(form);
    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    connectToMySQLServer();
    createDatabaseAndTable();

    loginFrame.setVisible(true);
}

    private void connectToMySQLServer() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true", "root", "23062712");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }

    private void createDatabaseAndTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS bms");
            stmt.execute("USE bms");
            stmt.execute("CREATE TABLE IF NOT EXISTS login(username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(50))");
            stmt.execute("INSERT IGNORE INTO login (username, password) VALUES ('admin', 'admin123')");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DB Setup Error: " + e.getMessage());
        }
    }

    private void showDashboard() {
    JFrame dashboardFrame = new JFrame("Dashboard - Boutique Management System");
    dashboardFrame.setSize(1366, 768);
    dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    dashboardFrame.getContentPane().setBackground(new Color(255, 240, 245)); 
    dashboardFrame.setLayout(null);
    dashboardFrame.setLocationRelativeTo(null);

    JPanel titlePanel = new JPanel();
    titlePanel.setBounds(0, 0, 1366, 80);
    titlePanel.setBackground(new Color(255, 182, 193));
    titlePanel.setLayout(null); 
    dashboardFrame.add(titlePanel);

JLabel titleLabel = new JLabel("Dashboard", SwingConstants.CENTER);
titleLabel.setBounds(0, 20, 1366, 50);
titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
titleLabel.setForeground(Color.WHITE);
titlePanel.add(titleLabel); 


    
    JLabel productLabel = new JLabel("Products");
    productLabel.setBounds(170, 100, 200, 30);
    productLabel.setFont(new Font("Arial", Font.BOLD, 24));
    productLabel.setForeground(new Color(60, 60, 60));
    dashboardFrame.add(productLabel);

    JPanel productPanel = new JPanel();
    productPanel.setLayout(null);
    productPanel.setBackground(Color.WHITE);
    productPanel.setBounds(100, 140, 300, 350);
    productPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    dashboardFrame.add(productPanel);

    JButton addProduct = new JButton("Add Product");
    addProduct.setBounds(25, 30, 250, 45);
    addProduct.setBackground(new Color(173, 216, 230));
    addProduct.setForeground(Color.WHITE);
    addProduct.setFont(new Font("Arial", Font.BOLD, 16));
    addProduct.setBorder(BorderFactory.createEmptyBorder());
    addProduct.setFocusPainted(false);
    productPanel.add(addProduct);

    JButton viewProduct = new JButton("View Products");
    viewProduct.setBounds(25, 90, 250, 45);
    viewProduct.setBackground(new Color(173, 216, 230));
    viewProduct.setForeground(Color.WHITE);
    viewProduct.setFont(new Font("Arial", Font.BOLD, 16));
    viewProduct.setBorder(BorderFactory.createEmptyBorder());
    viewProduct.setFocusPainted(false);
    productPanel.add(viewProduct);

    JButton updateProduct = new JButton("Update Product");
    updateProduct.setBounds(25, 150, 250, 45);
    updateProduct.setBackground(new Color(173, 216, 230));
    updateProduct.setForeground(Color.WHITE);
    updateProduct.setFont(new Font("Arial", Font.BOLD, 16));
    updateProduct.setBorder(BorderFactory.createEmptyBorder());
    updateProduct.setFocusPainted(false);
    productPanel.add(updateProduct);

    JButton deleteProduct = new JButton("Delete Product");
    deleteProduct.setBounds(25, 210, 250, 45);
    deleteProduct.setBackground(new Color(173, 216, 230));
    deleteProduct.setForeground(Color.WHITE);
    deleteProduct.setFont(new Font("Arial", Font.BOLD, 16));
    deleteProduct.setBorder(BorderFactory.createEmptyBorder());
    deleteProduct.setFocusPainted(false);
    productPanel.add(deleteProduct);

    JButton searchProduct = new JButton("Search Product");
    searchProduct.setBounds(25, 270, 250, 45); 
    searchProduct.setBackground(new Color(173, 216, 230));
    searchProduct.setForeground(Color.WHITE);
    searchProduct.setFont(new Font("Arial", Font.BOLD, 16));
    searchProduct.setBorder(BorderFactory.createEmptyBorder());
    searchProduct.setFocusPainted(false);
    productPanel.add(searchProduct);

    // ----- Customer Panel -----
    JLabel customerLabel = new JLabel("Customers");
    customerLabel.setBounds(570, 100, 200, 30);
    customerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    customerLabel.setForeground(new Color(60, 60, 60));
    dashboardFrame.add(customerLabel);

    JPanel customerPanel = new JPanel();
    customerPanel.setLayout(null);
    customerPanel.setBackground(Color.WHITE);
    customerPanel.setBounds(500, 140, 300, 350);
    customerPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    dashboardFrame.add(customerPanel);

    JButton addCustomer = new JButton("Add Customer");
    addCustomer.setBounds(25, 30, 250, 45);
    addCustomer.setBackground(new Color(144, 238, 144));
    addCustomer.setForeground(Color.WHITE);
    addCustomer.setFont(new Font("Arial", Font.BOLD, 16));
    addCustomer.setBorder(BorderFactory.createEmptyBorder());
    addCustomer.setFocusPainted(false);
    customerPanel.add(addCustomer);

    JButton viewCustomer = new JButton("View Customers");
    viewCustomer.setBounds(25, 90, 250, 45);
    viewCustomer.setBackground(new Color(144, 238, 144));
    viewCustomer.setForeground(Color.WHITE);
    viewCustomer.setFont(new Font("Arial", Font.BOLD, 16));
    viewCustomer.setBorder(BorderFactory.createEmptyBorder());
    viewCustomer.setFocusPainted(false);
    customerPanel.add(viewCustomer);

    JButton updateCustomer = new JButton("Update Customer");
    updateCustomer.setBounds(25, 150, 250, 45);
    updateCustomer.setBackground(new Color(144, 238, 144));
    updateCustomer.setForeground(Color.WHITE);
    updateCustomer.setFont(new Font("Arial", Font.BOLD, 16));
    updateCustomer.setBorder(BorderFactory.createEmptyBorder());
    updateCustomer.setFocusPainted(false);
    customerPanel.add(updateCustomer);

    JButton deleteCustomer = new JButton("Delete Customer");
    deleteCustomer.setBounds(25, 210, 250, 45);
    deleteCustomer.setBackground(new Color(144, 238, 144));
    deleteCustomer.setForeground(Color.WHITE);
    deleteCustomer.setFont(new Font("Arial", Font.BOLD, 16));
    deleteCustomer.setBorder(BorderFactory.createEmptyBorder());
    deleteCustomer.setFocusPainted(false);
    customerPanel.add(deleteCustomer);
    
    JButton searchCustomer = new JButton("Search Customer");
    searchCustomer.setBounds(25, 270, 250, 45);  
    searchCustomer.setBackground(new Color(144, 238, 144));
    searchCustomer.setForeground(Color.WHITE);
    searchCustomer.setFont(new Font("Arial", Font.BOLD, 16));
    searchCustomer.setBorder(BorderFactory.createEmptyBorder());
    searchCustomer.setFocusPainted(false);
    customerPanel.add(searchCustomer);


    // ----- Sales Panel -----
    JLabel salesLabel = new JLabel("Sales");
    salesLabel.setBounds(1000, 100, 200, 30);
    salesLabel.setFont(new Font("Arial", Font.BOLD, 24));
    salesLabel.setForeground(new Color(60, 60, 60));
    dashboardFrame.add(salesLabel);

    JPanel salesPanel = new JPanel();
    salesPanel.setLayout(null);
    salesPanel.setBackground(Color.WHITE);
    salesPanel.setBounds(900, 140, 300, 350);
    salesPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    dashboardFrame.add(salesPanel);

    JButton addSale = new JButton("Add Sale");
    addSale.setBounds(25, 30, 250, 45);
    addSale.setBackground(new Color(255, 182, 193));
    addSale.setForeground(Color.WHITE);
    addSale.setFont(new Font("Arial", Font.BOLD, 16));
    addSale.setBorder(BorderFactory.createEmptyBorder());
    addSale.setFocusPainted(false);
    salesPanel.add(addSale);

    JButton viewSales = new JButton("View Sales");
    viewSales.setBounds(25, 90, 250, 45);
    viewSales.setBackground(new Color(255, 182, 193));
    viewSales.setForeground(Color.WHITE);
    viewSales.setFont(new Font("Arial", Font.BOLD, 16));
    viewSales.setBorder(BorderFactory.createEmptyBorder());
    viewSales.setFocusPainted(false);
    salesPanel.add(viewSales);

    JButton updateSales = new JButton("Update Sale");
    updateSales.setBounds(25, 150, 250, 45);
    updateSales.setBackground(new Color(255, 182, 193));
    updateSales.setForeground(Color.WHITE);
    updateSales.setFont(new Font("Arial", Font.BOLD, 16));
    updateSales.setBorder(BorderFactory.createEmptyBorder());
    updateSales.setFocusPainted(false);
    salesPanel.add(updateSales);

    JButton deleteSales = new JButton("Delete Sale");
    deleteSales.setBounds(25, 210, 250, 45);
    deleteSales.setBackground(new Color(255, 182, 193));
    deleteSales.setForeground(Color.WHITE);
    deleteSales.setFont(new Font("Arial", Font.BOLD, 16));
    deleteSales.setBorder(BorderFactory.createEmptyBorder());
    deleteSales.setFocusPainted(false);
    salesPanel.add(deleteSales);
    
    JButton searchSales = new JButton("Search Sale");
    searchSales.setBounds(25, 270, 250, 45);  
    searchSales.setBackground(new Color(255, 182, 193));  
    searchSales.setForeground(Color.WHITE);
    searchSales.setFont(new Font("Arial", Font.BOLD, 16));
    searchSales.setBorder(BorderFactory.createEmptyBorder());
    searchSales.setFocusPainted(false);
    salesPanel.add(searchSales);


    // Add hover effects to all buttons
    JButton[] productButtons = {addProduct, viewProduct, updateProduct, deleteProduct, searchProduct};
    JButton[] customerButtons = {addCustomer, viewCustomer, updateCustomer, deleteCustomer, searchCustomer};
    JButton[] salesButtons = {addSale, viewSales, updateSales, deleteSales, searchSales};
    
    // Product buttons hover (light blue)
    for (JButton button : productButtons) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 230, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(173, 216, 230));
            }
        });
    }
    
    // Customer buttons hover (light green)
    for (JButton button : customerButtons) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(170, 250, 170));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(144, 238, 144));
            }
        });
    }
    
    // Sales buttons hover (light pink)
    for (JButton button : salesButtons) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 200, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 182, 193));
            }
        });
    }

    // ----- ActionListeners -----
    addProduct.addActionListener(e -> addProduct());
    viewProduct.addActionListener(e -> viewProducts());
    updateProduct.addActionListener(e -> updateProduct());
    deleteProduct.addActionListener(e -> deleteProduct());
    searchProduct.addActionListener(e -> searchProduct());

    addCustomer.addActionListener(e -> addCustomer());
    viewCustomer.addActionListener(e -> viewCustomer());
    updateCustomer.addActionListener(e -> updateCustomer());
    deleteCustomer.addActionListener(e -> deleteCustomer());
    searchCustomer.addActionListener(e -> searchCustomer());

    addSale.addActionListener(e -> addSales());
    viewSales.addActionListener(e -> viewSales());
    updateSales.addActionListener(e -> updateSales());
    deleteSales.addActionListener(e -> deleteSales());
    searchSales.addActionListener(e -> searchSales());

    dashboardFrame.setVisible(true);
}


    void addProduct() {
    JFrame frame = new JFrame("Add Product");
    frame.setSize(500, 400);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(230, 245, 255));
    
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    
    // Create center panel for form
    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBackground(new Color(230, 245, 255)); 
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    
    JTextField nameField = new JTextField();
    JTextField catField = new JTextField();
    JTextField sizeField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField qtyField = new JTextField();
    
    JLabel nameLabel = new JLabel("Name:");
    JLabel catLabel = new JLabel("Category:");
    JLabel sizeLabel = new JLabel("Size:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel qtyLabel = new JLabel("Quantity:");
    
    // Set fonts
    nameLabel.setFont(labelFont);
    catLabel.setFont(labelFont);
    sizeLabel.setFont(labelFont);
    priceLabel.setFont(labelFont);
    qtyLabel.setFont(labelFont);
    nameField.setFont(fieldFont);
    catField.setFont(fieldFont);
    sizeField.setFont(fieldFont);
    priceField.setFont(fieldFont);
    qtyField.setFont(fieldFont);
    
    formPanel.add(nameLabel); formPanel.add(nameField);
    formPanel.add(catLabel); formPanel.add(catField);
    formPanel.add(sizeLabel); formPanel.add(sizeField);
    formPanel.add(priceLabel); formPanel.add(priceField);
    formPanel.add(qtyLabel); formPanel.add(qtyField);
    
    JButton submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(135, 206, 250)); 
    submit.setForeground(Color.BLACK);
    
    formPanel.add(new JLabel()); formPanel.add(submit);
    
    frame.add(formPanel, BorderLayout.CENTER);
    
    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "INSERT INTO products (name, category, size, price, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, catField.getText());
            stmt.setString(3, sizeField.getText());
            stmt.setDouble(4, Double.parseDouble(priceField.getText()));
            stmt.setInt(5, Integer.parseInt(qtyField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Product added!");
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    });
    
    frame.setVisible(true);
}

     void addCustomer() {
    JFrame frame = new JFrame("Add Customer");
    frame.setSize(500, 400);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(235, 250, 235)); 

    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);

    // Create center panel for form
    JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
    formPanel.setBackground(new Color(235, 250, 235));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    JTextField nameField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField addressField = new JTextField();

    JLabel nameLabel = new JLabel("Name:");
    JLabel phoneLabel = new JLabel("Phone:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel addressLabel = new JLabel("Address:");

    // Set fonts
    nameLabel.setFont(labelFont);
    phoneLabel.setFont(labelFont);
    emailLabel.setFont(labelFont);
    addressLabel.setFont(labelFont);

    nameField.setFont(fieldFont);
    phoneField.setFont(fieldFont);
    emailField.setFont(fieldFont);
    addressField.setFont(fieldFont);

    formPanel.add(nameLabel); formPanel.add(nameField);
    formPanel.add(phoneLabel); formPanel.add(phoneField);
    formPanel.add(emailLabel); formPanel.add(emailField);
    formPanel.add(addressLabel); formPanel.add(addressField);

    JButton submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(144, 238, 144)); 
    submit.setForeground(Color.BLACK);

    formPanel.add(new JLabel()); formPanel.add(submit);
    frame.add(formPanel, BorderLayout.CENTER);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "INSERT INTO customers (name, phone, email, address) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, phoneField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, addressField.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Customer added!");
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


    void addSales() {
    JFrame frame = new JFrame("Add Sale");
    frame.setSize(500, 400);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(255, 240, 245));

    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);

    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBackground(new Color(255, 240, 245));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    JTextField dateField = new JTextField();
    JTextField customerIdField = new JTextField();
    JTextField productIdField = new JTextField();
    JTextField quantityField = new JTextField();
    JTextField totalField = new JTextField();

    JLabel dateLabel = new JLabel("Date:");
    JLabel customerIdLabel = new JLabel("Customer ID:");
    JLabel productIdLabel = new JLabel("Product ID:");
    JLabel quantityLabel = new JLabel("Quantity:");
    JLabel totalLabel = new JLabel("Total:");

    // Set fonts
    dateLabel.setFont(labelFont);
    customerIdLabel.setFont(labelFont);
    productIdLabel.setFont(labelFont);
    quantityLabel.setFont(labelFont);
    totalLabel.setFont(labelFont);

    dateField.setFont(fieldFont);
    customerIdField.setFont(fieldFont);
    productIdField.setFont(fieldFont);
    quantityField.setFont(fieldFont);
    totalField.setFont(fieldFont);

    formPanel.add(dateLabel); formPanel.add(dateField);
    formPanel.add(customerIdLabel); formPanel.add(customerIdField);
    formPanel.add(productIdLabel); formPanel.add(productIdField);
    formPanel.add(quantityLabel); formPanel.add(quantityField);
    formPanel.add(totalLabel); formPanel.add(totalField);

    JButton submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(255, 182, 193)); 
    submit.setForeground(Color.BLACK);

    formPanel.add(new JLabel()); formPanel.add(submit);
    frame.add(formPanel, BorderLayout.CENTER);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "INSERT INTO sales (sale_date, customer_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dateField.getText());
            stmt.setInt(2, Integer.parseInt(customerIdField.getText()));
            stmt.setInt(3, Integer.parseInt(productIdField.getText()));
            stmt.setInt(4, Integer.parseInt(quantityField.getText()));
            stmt.setInt(5, Integer.parseInt(totalField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Sale added!");
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


     void viewProducts() {
    JFrame frame = new JFrame("View Products");
    frame.setSize(600, 450);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(230, 245, 255));
    Font textFont = new Font("Arial", Font.PLAIN, 14);
    Font buttonFont = new Font("Arial", Font.BOLD, 16);

    JTextArea output = new JTextArea();
    output.setEditable(false);
    output.setFont(textFont);
    output.setBackground(Color.WHITE);
    output.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(output);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

    JButton viewBtn = new JButton("View Products");
    viewBtn.setFont(buttonFont);
    viewBtn.setBackground(new Color(135, 206, 250)); 
    viewBtn.setForeground(Color.BLACK);
    viewBtn.setFocusPainted(false);
    viewBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.setBackground(new Color(230, 245, 255));
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    bottomPanel.add(viewBtn);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    viewBtn.addActionListener(e -> {
        try {
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("product_id"))
                  .append(", Name: ").append(rs.getString("name"))
                  .append(", Category: ").append(rs.getString("category"))
                  .append(", Size: ").append(rs.getString("size"))
                  .append(", Price: ").append(rs.getInt("price"))
                  .append(", Quantity: ").append(rs.getInt("quantity"))
                  .append("\n");
            }
            output.setText(sb.toString().isEmpty() ? "No products found.\n" : sb.toString());
        } catch (Exception ex) {
            output.setText("View Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


    private void viewCustomer() {
    JFrame frame = new JFrame("View Customers");
    frame.setSize(600, 450);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(235, 250, 235)); 

    Font textFont = new Font("Arial", Font.PLAIN, 14);
    Font buttonFont = new Font("Arial", Font.BOLD, 16);

    JTextArea output = new JTextArea();
    output.setEditable(false);
    output.setFont(textFont);
    output.setBackground(Color.WHITE);
    output.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(output);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

    JButton viewBtn = new JButton("View Customers");
    viewBtn.setFont(buttonFont);
    viewBtn.setBackground(new Color(144, 238, 144));
    viewBtn.setForeground(Color.BLACK);
    viewBtn.setFocusPainted(false);
    viewBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.setBackground(new Color(235, 250, 235));
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    bottomPanel.add(viewBtn);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    viewBtn.addActionListener(e -> {
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("customer_id"))
                  .append(", Name: ").append(rs.getString("name"))
                  .append(", Phone: ").append(rs.getString("phone"))
                  .append(", Email: ").append(rs.getString("email"))
                  .append(", Address: ").append(rs.getString("address"))
                  .append("\n");
            }
            output.setText(sb.toString().isEmpty() ? "No customers found.\n" : sb.toString());
        } catch (Exception ex) {
            output.setText("View Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


    private void viewSales() {
    JFrame frame = new JFrame("View Sales");
    frame.setSize(600, 450);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(255, 240, 245));

    Font textFont = new Font("Arial", Font.PLAIN, 14);
    Font buttonFont = new Font("Arial", Font.BOLD, 16);

    JTextArea output = new JTextArea();
    output.setEditable(false);
    output.setFont(textFont);
    output.setBackground(Color.WHITE);
    output.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(output);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

    JButton viewBtn = new JButton("View Sales");
    viewBtn.setFont(buttonFont);
    viewBtn.setBackground(new Color(255, 182, 193)); 
    viewBtn.setForeground(Color.BLACK);
    viewBtn.setFocusPainted(false);
    viewBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.setBackground(new Color(255, 240, 245));
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    bottomPanel.add(viewBtn);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    viewBtn.addActionListener(e -> {
        try {
            String sql = "SELECT * FROM sales";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Sale ID: ").append(rs.getInt("sale_id"))
                  .append(", Date: ").append(rs.getDate("sale_date"))
                  .append(", Product ID: ").append(rs.getInt("product_id"))
                  .append(", Customer ID: ").append(rs.getInt("customer_id"))
                  .append(", Quantity: ").append(rs.getInt("quantity"))
                  .append(", Total Price: ").append(rs.getInt("total_price"))
                  .append("\n");
            }
            output.setText(sb.toString().isEmpty() ? "No sales found.\n" : sb.toString());
        } catch (Exception ex) {
            output.setText("View Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


    
    void updateProduct() {
    JFrame frame = new JFrame("Update Product");
    frame.setSize(500, 450);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(230, 245, 255)); // Light pink

    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);

    // Center panel for the form with padding and spacing
    JPanel formPanel = new JPanel(new GridLayout(7, 2, 15, 15));
    formPanel.setBackground(new Color(230, 245, 255));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField catField = new JTextField();
    JTextField sizeField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField qtyField = new JTextField();

    JLabel idLabel = new JLabel("Product ID:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel catLabel = new JLabel("Category:");
    JLabel sizeLabel = new JLabel("Size:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel qtyLabel = new JLabel("Quantity:");

    // Set fonts for labels and fields
    idLabel.setFont(labelFont);
    nameLabel.setFont(labelFont);
    catLabel.setFont(labelFont);
    sizeLabel.setFont(labelFont);
    priceLabel.setFont(labelFont);
    qtyLabel.setFont(labelFont);

    idField.setFont(fieldFont);
    nameField.setFont(fieldFont);
    catField.setFont(fieldFont);
    sizeField.setFont(fieldFont);
    priceField.setFont(fieldFont);
    qtyField.setFont(fieldFont);

    formPanel.add(idLabel); formPanel.add(idField);
    formPanel.add(nameLabel); formPanel.add(nameField);
    formPanel.add(catLabel); formPanel.add(catField);
    formPanel.add(sizeLabel); formPanel.add(sizeField);
    formPanel.add(priceLabel); formPanel.add(priceField);
    formPanel.add(qtyLabel); formPanel.add(qtyField);

    JButton submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(135, 206, 250));
    submit.setForeground(Color.BLACK);

    formPanel.add(new JLabel()); formPanel.add(submit);

    frame.add(formPanel, BorderLayout.CENTER);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "UPDATE products SET name=?, category=?, size=?, price=?, quantity=? WHERE product_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, catField.getText());
            stmt.setString(3, sizeField.getText());
            stmt.setDouble(4, Double.parseDouble(priceField.getText()));
            stmt.setInt(5, Integer.parseInt(qtyField.getText()));
            stmt.setInt(6, Integer.parseInt(idField.getText()));

            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, rows > 0 ? "Updated!" : "Product not found.");
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Update Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}

    void updateCustomer() {
    JFrame frame = new JFrame("Update Customer");
    frame.setSize(500, 420);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(235, 250, 235)); // Light pink

    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);

    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBackground(new Color(235, 250, 235));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField addressField = new JTextField();

    JLabel idLabel = new JLabel("Customer ID:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel phoneLabel = new JLabel("Phone:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel addressLabel = new JLabel("Address:");

    idLabel.setFont(labelFont);
    nameLabel.setFont(labelFont);
    phoneLabel.setFont(labelFont);
    emailLabel.setFont(labelFont);
    addressLabel.setFont(labelFont);

    idField.setFont(fieldFont);
    nameField.setFont(fieldFont);
    phoneField.setFont(fieldFont);
    emailField.setFont(fieldFont);
    addressField.setFont(fieldFont);

    formPanel.add(idLabel); formPanel.add(idField);
    formPanel.add(nameLabel); formPanel.add(nameField);
    formPanel.add(phoneLabel); formPanel.add(phoneField);
    formPanel.add(emailLabel); formPanel.add(emailField);
    formPanel.add(addressLabel); formPanel.add(addressField);

    JButton submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(144, 238, 144)); // Light pink button
    submit.setForeground(Color.BLACK);

    formPanel.add(new JLabel()); formPanel.add(submit);

    frame.add(formPanel, BorderLayout.CENTER);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "UPDATE customers SET name=?, phone=?, email=?, address=? WHERE customer_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, phoneField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, addressField.getText());
            stmt.setInt(5, Integer.parseInt(idField.getText()));

            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, rows > 0 ? "Updated!" : "Customer not found.");
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Update Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


    
    void updateSales() {
    JFrame frame = new JFrame("Update Sale");
    frame.setSize(500, 450);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(255, 240, 245)); // Light pink background

    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font fieldFont = new Font("Arial", Font.PLAIN, 14);

    JPanel formPanel = new JPanel(new GridLayout(7, 2, 15, 15));
    formPanel.setBackground(new Color(255, 240, 245));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    JTextField idField = new JTextField();
    JTextField dateField = new JTextField();
    JTextField customerIdField = new JTextField();
    JTextField productIdField = new JTextField();
    JTextField quantityField = new JTextField();
    JTextField totalField = new JTextField();

    JLabel idLabel = new JLabel("Sale ID:");
    JLabel dateLabel = new JLabel("Date:");
    JLabel customerIdLabel = new JLabel("Customer ID:");
    JLabel productIdLabel = new JLabel("Product ID:");
    JLabel quantityLabel = new JLabel("Quantity:");
    JLabel totalLabel = new JLabel("Total:");

    idLabel.setFont(labelFont);
    dateLabel.setFont(labelFont);
    customerIdLabel.setFont(labelFont);
    productIdLabel.setFont(labelFont);
    quantityLabel.setFont(labelFont);
    totalLabel.setFont(labelFont);

    idField.setFont(fieldFont);
    dateField.setFont(fieldFont);
    customerIdField.setFont(fieldFont);
    productIdField.setFont(fieldFont);
    quantityField.setFont(fieldFont);
    totalField.setFont(fieldFont);

    formPanel.add(idLabel); formPanel.add(idField);
    formPanel.add(dateLabel); formPanel.add(dateField);
    formPanel.add(customerIdLabel); formPanel.add(customerIdField);
    formPanel.add(productIdLabel); formPanel.add(productIdField);
    formPanel.add(quantityLabel); formPanel.add(quantityField);
    formPanel.add(totalLabel); formPanel.add(totalField);

    JButton submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(255, 182, 193)); // Light pink button
    submit.setForeground(Color.BLACK);

    formPanel.add(new JLabel()); formPanel.add(submit);

    frame.add(formPanel, BorderLayout.CENTER);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "UPDATE sales SET sale_date=?, customer_id=?, product_id=?, quantity=?, total_price=? WHERE sale_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dateField.getText());
            stmt.setInt(2, Integer.parseInt(customerIdField.getText()));
            stmt.setInt(3, Integer.parseInt(productIdField.getText()));
            stmt.setInt(4, Integer.parseInt(quantityField.getText()));
            stmt.setDouble(5, Double.parseDouble(totalField.getText()));
            stmt.setInt(6, Integer.parseInt(idField.getText()));

            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, rows > 0 ? "Updated!" : "Sale not found.");
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Update Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}


    
    void deleteProduct() {
    JFrame frame = new JFrame("Delete Product");
    frame.setSize(450, 250);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(230, 245, 255)); // Light pink background

    JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    panel.setBackground(new Color(230, 245, 255));

    JLabel idLabel = new JLabel("Enter Product ID to delete:");
    idLabel.setFont(new Font("Arial", Font.BOLD, 16));
    JTextField idField = new JTextField();
    idField.setFont(new Font("Arial", Font.PLAIN, 14));

    panel.add(idLabel);
    panel.add(idField);

    JButton submit = new JButton("Delete");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(135, 206, 250)); // Light pink button
    submit.setForeground(Color.BLACK);

    JTextArea output = new JTextArea(3, 20);
    output.setEditable(false);
    output.setFont(new Font("Arial", Font.PLAIN, 14));
    output.setBackground(new Color(230, 245, 255));
    output.setLineWrap(true);
    output.setWrapStyleWord(true);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
    bottomPanel.setBackground(new Color(230, 245, 255));
    bottomPanel.add(submit, BorderLayout.WEST);
    bottomPanel.add(new JScrollPane(output), BorderLayout.CENTER);

    frame.add(panel, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "DELETE FROM products WHERE product_id=?";  // Adjust column name to your DB
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText().trim()));
            int rows = stmt.executeUpdate();
            output.setText(rows > 0 ? "Deleted successfully!" : "Product ID not found.");
        } catch (Exception ex) {
            output.setText("Delete Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}

    
     void deleteCustomer() {
    JFrame frame = new JFrame("Delete Customer");
    frame.setSize(450, 250);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(235, 250, 235)); // Light pink background

    JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    panel.setBackground(new Color(235, 250, 235));

    JLabel idLabel = new JLabel("Enter Customer ID to delete:");
    idLabel.setFont(new Font("Arial", Font.BOLD, 16));
    JTextField idField = new JTextField();
    idField.setFont(new Font("Arial", Font.PLAIN, 14));

    panel.add(idLabel);
    panel.add(idField);

    JButton submit = new JButton("Delete");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(144, 238, 144)); // Light pink button
    submit.setForeground(Color.BLACK);

    JTextArea output = new JTextArea(3, 20);
    output.setEditable(false);
    output.setFont(new Font("Arial", Font.PLAIN, 14));
    output.setBackground(new Color(235, 250, 235));
    output.setLineWrap(true);
    output.setWrapStyleWord(true);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
    bottomPanel.setBackground(new Color(235, 250, 235));
    bottomPanel.add(submit, BorderLayout.WEST);
    bottomPanel.add(new JScrollPane(output), BorderLayout.CENTER);

    frame.add(panel, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "DELETE FROM customers WHERE customer_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText().trim()));
            int rows = stmt.executeUpdate();
            output.setText(rows > 0 ? "Deleted successfully!" : "Customer ID not found.");
        } catch (Exception ex) {
            output.setText("Delete Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}

    void deleteSales() {
    JFrame frame = new JFrame("Delete Sale");
    frame.setSize(450, 250);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(255, 240, 245)); // Light pink background

    JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    panel.setBackground(new Color(255, 240, 245));

    JLabel idLabel = new JLabel("Enter Sale ID to delete:");
    idLabel.setFont(new Font("Arial", Font.BOLD, 16));
    JTextField idField = new JTextField();
    idField.setFont(new Font("Arial", Font.PLAIN, 14));

    panel.add(idLabel);
    panel.add(idField);

    JButton submit = new JButton("Delete");
    submit.setFont(new Font("Arial", Font.BOLD, 16));
    submit.setBackground(new Color(255, 182, 193)); // Light pink button
    submit.setForeground(Color.BLACK);

    JTextArea output = new JTextArea(3, 20);
    output.setEditable(false);
    output.setFont(new Font("Arial", Font.PLAIN, 14));
    output.setBackground(new Color(255, 240, 245));
    output.setLineWrap(true);
    output.setWrapStyleWord(true);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
    bottomPanel.setBackground(new Color(255, 240, 245));
    bottomPanel.add(submit, BorderLayout.WEST);
    bottomPanel.add(new JScrollPane(output), BorderLayout.CENTER);

    frame.add(panel, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    submit.addActionListener(e -> {
        try {
            conn.createStatement().execute("USE bms");
            String sql = "DELETE FROM sales WHERE sale_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText().trim()));
            int rows = stmt.executeUpdate();
            output.setText(rows > 0 ? "Deleted successfully!" : "Sale ID not found.");
        } catch (Exception ex) {
            output.setText("Delete Error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}
    
    void searchProduct() {
        JFrame frame = new JFrame("Search Product");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(230, 245, 255));  

        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Output area with scroll
        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setFont(textFont);
        output.setBackground(Color.WHITE);
        output.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Button to trigger product search input dialog
        JButton searchBtn = new JButton("Search Product");
        searchBtn.setFont(buttonFont);
        searchBtn.setBackground(new Color(135, 206, 250));  // Your original green
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bottom panel holding the button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(230, 245, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bottomPanel.add(searchBtn);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action listener to perform the product search on button click
        searchBtn.addActionListener(e -> {
            String productId = JOptionPane.showInputDialog("Enter Product ID:");
            if (productId != null && !productId.isEmpty()) {
                try {
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE product_id = ?");
                    stmt.setString(1, productId);
                    ResultSet rs = stmt.executeQuery();
                    output.setText("");
                    if (rs.next()) {
                        output.append("ID: " + rs.getInt("product_id") + "\n");
                        output.append("Name: " + rs.getString("name") + "\n");
                        output.append("Category: " + rs.getString("category") + "\n");
                        output.append("Size: " + rs.getString("size") + "\n");
                        output.append("Price: " + rs.getInt("price") + "\n");
                        output.append("Quantity: " + rs.getInt("quantity") + "\n");
                    } else {
                        output.setText("No product found with ID " + productId);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    output.setText("Error fetching product data.");
                }
            }
        });

        frame.setVisible(true);
    }
    
    void searchCustomer() {
        JFrame frame = new JFrame("Search Customer");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(235, 250, 235)); // Same light greenish background

        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Output area
        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setFont(textFont);
        output.setBackground(Color.WHITE);
        output.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Search button
        JButton searchBtn = new JButton("Search Customer");
        searchBtn.setFont(buttonFont);
        searchBtn.setBackground(new Color(144, 238, 144));  // Keep your green color
        searchBtn.setForeground(Color.BLACK);               // Black text as in your viewCustomer button
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(235, 250, 235));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bottomPanel.add(searchBtn);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action listener for searching customer
        searchBtn.addActionListener(e -> {
            String customerId = JOptionPane.showInputDialog("Enter Customer ID:");
            if (customerId != null && !customerId.isEmpty()) {
                try {
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
                    stmt.setString(1, customerId);
                    ResultSet rs = stmt.executeQuery();
                    output.setText("");
                    if (rs.next()) {
                        output.append("ID: " + rs.getInt("customer_id") + "\n");
                        output.append("Name: " + rs.getString("name") + "\n");
                        output.append("Phone: " + rs.getString("phone") + "\n");
                        output.append("Email: " + rs.getString("email") + "\n");
                        output.append("Address: " + rs.getString("address") + "\n");
                    } else {
                        output.setText("No customer found with ID " + customerId);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    output.setText("Error fetching customer data.");
                }
            }
        });

        frame.setVisible(true);
    }
    
    void searchSales() {
        JFrame frame = new JFrame("Search Sale");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 240, 245)); // Same light pink background

        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Output text area
        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setFont(textFont);
        output.setBackground(Color.WHITE);
        output.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Search Sale button
        JButton searchBtn = new JButton("Search Sale");
        searchBtn.setFont(buttonFont);
        searchBtn.setBackground(new Color(255, 182, 193)); // Same light pink button color
        searchBtn.setForeground(Color.BLACK);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bottomPanel.add(searchBtn);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action listener to handle search
        searchBtn.addActionListener(e -> {
            String saleId = JOptionPane.showInputDialog("Enter Sale ID:");
            if (saleId != null && !saleId.isEmpty()) {
                try {
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sales WHERE sale_id = ?");
                    stmt.setString(1, saleId);
                    ResultSet rs = stmt.executeQuery();
                    output.setText("");
                    if (rs.next()) {
                        output.append("Sale ID: " + rs.getInt("sale_id") + "\n");
                        output.append("Sale Date: " + rs.getString("sale_date") + "\n");
                        output.append("Customer ID: " + rs.getInt("customer_id") + "\n");
                        output.append("Product ID: " + rs.getInt("product_id") + "\n"); 
                        output.append("Quantity: " + rs.getInt("quantity") + "\n");
                        output.append("Total Price: " + rs.getInt("total_price") + "\n");
                        
                    } else {
                        output.setText("No sale found with ID " + saleId);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    output.setText("Error fetching sale data.");
                }
            }
        });
        
        frame.setVisible(true);
    }




    
    public static void main(String[] args) {
        new BoutiqueManagementSystem2();
    }
}
