# ShopSphere — RTU Advanced Java E-Commerce Project

**ShopSphere** is a complete, production-grade Java Enterprise E-Commerce Management System designed as a final practical capstone project adhering strictly to the **Rajasthan Technical University (RTU) Advanced Java syllabus**.

The project brings together modern Web technologies (Jakarta Servlet 6.1, JSP 3.1, JSTL, EL, Bootstrap 5) on Apache Tomcat 11 with distributed enterprise modules (Java RMI, java.net Socket/ServerSocket TCP networking, and Java Swing MVC Desktop Administration).

---

## 🌟 RTU Syllabus Coverage Matrix

| RTU Syllabus Topic | Project Implementation & Location |
| :--- | :--- |
| **Swing & Components** | `swing-admin/` module: `LoginFrame`, `AdminDashboardFrame`, `JTable`, `JComboBox`, `JCheckBox`, `JOptionPane` |
| **Swing MVC** | `ProductTableModel`, `OrderTableModel`, `CustomerTableModel` (Model), `LoginFrame` (View), `AdminController` (Controller) |
| **Pluggable Look & Feel** | `UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())` in `LoginFrame.java` |
| **JDBC & java.sql** | `core/src/main/java/com/shopsphere/dao/`: 10 DAOs utilizing `PreparedStatement`, `Connection`, `ResultSet` |
| **MySQL Database** | `database/shopsphere.sql`: 12 relational tables with foreign keys and sample seed data |
| **java.net Networking** | `network/` module: `ShopSphereInventoryServer` (`ServerSocket`) & `ShopSphereInventoryClient` (`Socket`) on port 8088 |
| **Object Serialization** | `Product`, `Order`, `NetworkCommand`, `NetworkResponse` implementing `Serializable` (`serialVersionUID`) |
| **Java RMI & Registry** | `rmi/` module: `InventoryService` (`Remote`), `InventoryServiceImpl` (`UnicastRemoteObject`), `RmiServerMain` (port 1099) |
| **JNDI & Naming** | `demo/jndi`: `InitialContext`, `Context.lookup("java:comp/env")` resource resolution demonstration |
| **URL & Protocol Handlers** | `demo/url-protocol`: `java.net.URL`, `URLConnection` headers, MIME types, and stream inspection |
| **Internationalization (I18N)** | `messages_en.properties`, `messages_hi.properties`, `Locale`, `ResourceBundle`, `NumberFormat`, `DateFormat` |
| **Jakarta Servlet Lifecycle** | `demo/servlet-lifecycle`: `init(ServletConfig)`, `service()`, `doGet()`, `destroy()`, `ServletContext` |
| **Filters** | `AuthenticationFilter` (protects customer routes), `AdminFilter` (protects `/admin/*`), `EncodingFilter` (UTF-8) |
| **Event Listeners** | `AppListener`: `ServletContextListener` and `HttpSessionListener` tracking active sessions |
| **Session & Cookies** | `HttpSession` auth state, server-side cart count, remember-me email cookies |
| **JSP Architecture & Syntax** | `demo/jsp-elements.jsp`: Declarations (`<%! %>`), Scriptlets (`<% %>`), Expressions (`<%= %>`), Comments (`<%-- %>`) |
| **JSP Fragments & Tag Files** | `fragments/` (`header.jsp`, `navbar.jsp`, `footer.jsp`, `sidebar.jsp`) & `WEB-INF/tags/` (`productCard.tag`, `rating.tag`) |
| **Tag Extensions (Custom Tags)** | Custom tag handlers: `FormatCurrencyTag`, `StockBadgeTag` with `WEB-INF/tld/shopsphere.tld` |
| **JSTL Tags** | JSTL Core (`c:forEach`, `c:if`, `c:choose`), JSTL XML (`x:parse`, `x:out`), JSTL SQL (`sql:query`) |
| **Applet Architecture** | `demo/applet-demo.jsp`: Comprehensive interactive simulator, 5 lifecycle methods, and NPAPI deprecation guide |

---

## 🏗️ Project Architecture & Layout

```
ShopSphere/
├── pom.xml                       # Root Maven multi-module orchestrator
├── setup_database.bat            # One-click MySQL database installer
├── build_all.bat                 # Full project Maven build script
├── deploy_tomcat.bat             # Deploys shopsphere.war to Tomcat webapps
├── start_tomcat.bat              # Starts Apache Tomcat 11
├── run_swing_admin.bat           # Launches Java Swing Desktop Admin portal
├── run_socket_server.bat         # Launches TCP Socket Inventory Server (port 8088)
├── run_socket_client.bat         # Launches interactive Socket Client
├── run_rmi_server.bat            # Launches RMI Registry & Service (port 1099)
├── run_rmi_client.bat            # Launches RMI Remote Client
│
├── database/
│   └── shopsphere.sql            # Complete DDL & DML script (12 tables + seed data)
│
├── core/                         # Shared Business Layer (JAR)
│   └── src/main/java/com/shopsphere/
│       ├── model/                # 11 Serializable Domain POJOs
│       ├── dao/                  # 10 Data Access Objects (JDBC PreparedStatement)
│       ├── service/              # Service Layer (Product, Order, Checkout, User)
│       └── util/                 # DBUtil, PasswordUtil (SHA-256), I18nUtil
│
├── web/                          # Customer & Admin Web Store (WAR)
│   └── src/main/
│       ├── java/com/shopsphere/web/
│       │   ├── controller/       # Jakarta Servlets (Store, Cart, Checkout, Admin, Demos)
│       │   ├── filter/           # AuthenticationFilter, AdminFilter, EncodingFilter
│       │   ├── listener/         # AppListener (ServletContext & HttpSession)
│       │   └── tag/              # FormatCurrencyTag, StockBadgeTag handlers
│       └── webapp/               # JSP Views, Fragments, Tags, TLD, CSS, JS
│
├── swing-admin/                  # Desktop Admin Application (Executable JAR)
│   └── src/main/java/com/shopsphere/swing/
│       ├── model/                # ProductTableModel, OrderTableModel, CustomerTableModel
│       ├── view/                 # LoginFrame, AdminDashboardFrame
│       └── controller/           # AdminController, LoginController
│
├── network/                      # java.net Socket/ServerSocket Module
│   └── src/main/java/com/shopsphere/network/
│       ├── protocol/             # NetworkCommand, NetworkResponse
│       ├── server/               # ShopSphereInventoryServer (Port 8088)
│       └── client/               # ShopSphereInventoryClient
│
└── rmi/                          # Java RMI Distributed Module
    └── src/main/java/com/shopsphere/rmi/
        ├── remote/               # InventoryService (Remote Interface)
        ├── server/               # InventoryServiceImpl, RmiServerMain (Port 1099)
        └── client/               # RmiClientMain
```

---

## 🚀 Quick Setup & Execution Guide

### 1. Database Initialization
Double-click `setup_database.bat` or run:
```powershell
Get-Content database\shopsphere.sql | & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```
*(If your MySQL password is not `root`, edit `core/src/main/resources/db.properties` with your credentials and rebuild)*.

### 2. Build the Multi-Module Project
Run the build script:
```cmd
build_all.bat
```
This compiles all modules and outputs:
- `web\target\shopsphere.war`
- `swing-admin\target\shopsphere-swing-admin-1.0.0.jar`
- `network\target\shopsphere-network-1.0.0.jar`
- `rmi\target\shopsphere-rmi-1.0.0.jar`

### 3. Deploy and Start Web Application
1. Run `deploy_tomcat.bat` to copy `shopsphere.war` to Tomcat's `webapps` directory.
2. Run `start_tomcat.bat` to boot Tomcat 11.
3. Open your browser at: **`http://localhost:8080/shopsphere/`**

### 4. Run the Swing Desktop Admin Portal
Double-click `run_swing_admin.bat` to open the Swing GUI.
- **Admin Email:** `admin@shopsphere.com`
- **Password:** `admin123`

### 5. Run the Sockets Network Module
1. In Terminal 1: Run `run_socket_server.bat` (Listens on port 8088).
2. In Terminal 2: Run `run_socket_client.bat` to execute commands (`GET_PRODUCT`, `GET_STOCK`, `SEARCH_PRODUCT`) or automated demo batches.

### 6. Run the Java RMI Distributed Module
1. In Terminal 1: Run `run_rmi_server.bat` (Registers `InventoryService` on port 1099).
2. In Terminal 2: Run `run_rmi_client.bat` to invoke remote methods and view deserialized output.

---

## 🔑 Default Demonstration Credentials

| Role | Email | Password | Access Rights |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin@shopsphere.com` | `admin123` | Full Admin Portal, Desktop Swing Admin, Product CRUD, Order Status |
| **Customer** | `sneha@example.com` | `sneha123` | Web Store, Shopping Cart, Wishlist, Checkout, Order Tracking |
| **Customer** | `rahul@example.com` | `rahul123` | Web Store, Shopping Cart, Orders, Reviews |

---

## 🎓 RTU Final Viva Demonstration Tips

1. **N-Tier Architecture Explanation:**
   Be ready to trace a request end-to-end:
   `Browser / JSP (View)` &rarr; `CartServlet / CheckoutServlet (Controller)` &rarr; `CheckoutService (Business Logic)` &rarr; `OrderDAO / ProductDAO (Data Access)` &rarr; `DBUtil (Connection Management)` &rarr; `MySQL Database`.
2. **PreparedStatement Security:**
   Point to `UserDAO.java` and explain that user input is never concatenated into SQL strings, preventing SQL Injection vulnerabilities.
3. **Pluggable Look & Feel:**
   Show `LoginFrame.java` line `UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());` which binds native OS rendering to Swing widgets.
4. **Serialization & Sockets:**
   Demonstrate `ShopSphereInventoryServer` accepting a `NetworkCommand` over `ObjectInputStream` and writing serialized `Product` objects over `ObjectOutputStream`.
5. **RMI Registry Lookup:**
   Show `LocateRegistry.createRegistry(1099)` in `RmiServerMain` and `registry.lookup("InventoryService")` in `RmiClientMain`.
