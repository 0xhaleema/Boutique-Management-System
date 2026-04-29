# 🛍️ Boutique Management System

A desktop application built with **Java Swing** and **MySQL** that streamlines day-to-day boutique operations — from managing inventory and customers to tracking sales — all through a clean, intuitive interface.

---

## 📸 Screenshots

| Splash Screen | Dashboard |
|---|---|
| ![Splash](splash.png) | ![Dashboard](dashboard.png) |

---

## ✨ Features

- 🔐 **Login Screen** — Secure entry point with a boutique-themed splash UI
- 📦 **Product Management** — Add, view, update, delete, and search products
- 👥 **Customer Management** — Maintain a complete customer database with full CRUD support
- 💰 **Sales Management** — Record, track, update, and search sales transactions
- 🗄️ **Persistent Storage** — All data stored and retrieved via MySQL database

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Programming Language | Java |
| GUI Toolkit | Java Swing |
| Database | MySQL |

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or higher
- MySQL Server
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/boutique-management-system.git
   cd boutique-management-system
   ```

2. **Configure the database**
   - Create a MySQL database named `boutique_db`
   - Import the provided SQL schema:
     ```bash
     mysql -u root -p boutique_db < schema.sql
     ```

3. **Update database credentials**
   - Open the database configuration file (e.g., `DBConnection.java`)
   - Update the host, username, and password to match your MySQL setup

4. **Run the application**
   - Open the project in your IDE
   - Build and run `Main.java`

---

## 📁 Project Structure

```
boutique-management-system/
├── src/
│   ├── main/
│   │   └── Main.java
│   ├── ui/
│   │   ├── SplashScreen.java
│   │   ├── Dashboard.java
│   │   ├── ProductPanel.java
│   │   ├── CustomerPanel.java
│   │   └── SalesPanel.java
│   ├── dao/
│   │   ├── ProductDAO.java
│   │   ├── CustomerDAO.java
│   │   └── SalesDAO.java
│   └── db/
│       └── DBConnection.java
├── schema.sql
└── README.md
```

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

<p align="center">Made with ❤️ using Java & MySQL</p>
