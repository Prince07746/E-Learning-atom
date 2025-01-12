Here's a **step-by-step guide** to troubleshoot and resolve PostgreSQL connection issues in a Spring Boot application. You can save this documentation for future reference.

---

## **PostgreSQL Connection Troubleshooting for Spring Boot**

### **1. Install PostgreSQL**
Ensure PostgreSQL is installed on your machine:
- Download PostgreSQL from the [official website](https://www.postgresql.org/download/).
- During installation, note the username (default: `postgres`) and the password you set for the superuser.

---

### **2. Configure System PATH**
Add PostgreSQL's `bin` directory to your system `PATH` environment variable:
1. Locate the `bin` folder (e.g., `C:\Program Files\PostgreSQL\<version>\bin`).
2. Add it to the system `PATH` under environment variables.
3. Restart your terminal or command prompt.

---

### **3. Verify PostgreSQL Installation**
Run the following command to check if PostgreSQL is accessible:
```bash
psql --version
```

---

### **4. Create or Verify Database and User**
#### **4.1. Log in as Superuser**
Connect using the `postgres` user:
```bash
psql -U postgres
```

#### **4.2. Create a New Database**
Run the following SQL:
```sql
CREATE DATABASE userDB;
```

#### **4.3. Create a New User**
Replace `<password>` with your desired password:
```sql
CREATE ROLE david WITH LOGIN PASSWORD '<password>';
```

#### **4.4. Grant Privileges**
Grant access to the `userDB` database:
```sql
GRANT ALL PRIVILEGES ON DATABASE userDB TO david;
```

---

### **5. Update Spring Boot Application Configuration**
In your `application.properties` file, configure the PostgreSQL connection:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/userDB
spring.datasource.username=david
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

---

### **6. Test the Application**
Run your Spring Boot application:
```bash
./mvnw spring-boot:run
```

---

### **7. Common Errors and Solutions**
#### **7.1. Role Does Not Exist**
Error:
```bash
ERROR: role "david" does not exist
```
**Solution**: Create the role using:
```sql
CREATE ROLE david WITH LOGIN PASSWORD '<password>';
```

#### **7.2. Password Authentication Failed**
Error:
```bash
FATAL: password authentication failed for user "david"
```
**Solution**: Update the user's password:
```sql
ALTER ROLE david WITH PASSWORD '<password>';
```

#### **7.3. Cannot Connect to Database**
Error:
```bash
FATAL: database "userDB" does not exist
```
**Solution**: Create the database:
```sql
CREATE DATABASE userDB;
```

---

### **8. Verify PostgreSQL Access**
To manually test the connection:
```bash
psql -U david -d userDB
```
If it works, the Spring Boot application should connect successfully.

---



The error indicates that the user you are using to connect to the database does not have the necessary permissions to create tables in the `public` schema. Here's how to resolve this issue:

---

## **Solution: Grant Permissions to the User**

1. **Log in as the Superuser (`postgres`)**  
   Open a terminal or command prompt and log in to PostgreSQL using the `postgres` user:
   ```bash
   psql -U postgres
   ```

2. **Grant Necessary Permissions to the User**  
   Replace `david` with your database username. Execute the following SQL commands:
   ```sql
   -- Connect to the target database
   \c userDB

   -- Grant usage and create privileges on the schema
   GRANT USAGE ON SCHEMA public TO david;
   GRANT CREATE ON SCHEMA public TO david;

   -- Grant all privileges on the schema (if needed)
   ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO david;
   GRANT ALL PRIVILEGES ON SCHEMA public TO david;
   ```

3. **Test the Permissions**  
   Exit `psql` and test if the user can now create tables:
   ```bash
   psql -U david -d userDB
   ```
   Run a simple table creation SQL command to verify:
   ```sql
   CREATE TABLE test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));
   ```

4. **Update Your Spring Boot Application**  
   Restart your Spring Boot application. Hibernate should now successfully execute the DDL commands to create tables.

---

## **Alternative Solution: Change the Hibernate DDL Auto Setting**
If you do not want Hibernate to manage schema creation, you can update the `application.properties` file to use an alternative schema generation strategy:
```properties
spring.jpa.hibernate.ddl-auto=validate
```
This will ensure that Hibernate validates the schema but does not attempt to create or modify it.

---

## **Why This Happens**
The error occurs because the PostgreSQL user lacks:
1. Usage privileges on the `public` schema.
2. Create privileges on the `public` schema.
3. Default privileges to create tables.

By granting these permissions, you resolve the issue and allow Hibernate to manage schema creation as needed.

