# EJB Remote Session Bean Demo

Dự án này đã được cập nhật để bao gồm demo đầy đủ của Remote Session Bean.

## Các loại EJB được demo:

### 1. Session Bean Local
- **Interface**: `MySessionBeanLocal`
- **Methods**: `addUser(String)`, `getAllUserNames()`
- **Usage**: Được inject trực tiếp vào Servlet bằng `@EJB`
- **Demo**: Thông qua form "Add User" trên trang chính

### 2. Session Bean Remote  
- **Interface**: `MySessionBeanRemote`
- **Methods**: `getGreeting(String)`
- **Usage**: Truy cập qua JNDI lookup
- **Demo**: Có 2 cách demo

#### Cách 1: Web-based Remote Client
- Truy cập: `http://localhost:8080/ejb-demo/remote-client`
- Servlet `RemoteClientServlet` thực hiện JNDI lookup
- Giao diện web để test Remote EJB

#### Cách 2: Standalone Remote Client
- File: `RemoteEJBClient.java`
- Chạy từ command line bằng script `run-remote-client.bat`
- Client độc lập chạy ngoài application server

### 3. Message-Driven Bean (MDB)
- **Interface**: `MessageListener`
- **Usage**: Nhận message từ JMS Queue
- **Demo**: Thông qua form "Send Message" trên trang chính

## JNDI Names for Remote EJB

GlassFish tự động tạo các JNDI name cho Remote EJB:

```
java:global/ejb-demo-ear-1.0-SNAPSHOT/ejb-demo-ejb-1.0-SNAPSHOT/MySessionBean!com.example.api.MySessionBeanRemote
```

Hoặc có thể là:
```
java:global/ejb-demo/ejb-demo-ejb/MySessionBean!com.example.api.MySessionBeanRemote
```

## Cách chạy demo:

### 1. Deploy ứng dụng:
```bash
mvn clean package
# Deploy file ejb-demo-ear/target/ejb-demo-ear-1.0-SNAPSHOT.ear lên GlassFish
```

### 2. Test Remote EJB qua Web:
- Truy cập: `http://localhost:8080/ejb-demo/app`
- Click "Test Remote EJB Client"
- Nhập tên và xem kết quả

### 3. Test Remote EJB qua Standalone Client:
```bash
# Chỉnh sửa đường dẫn GlassFish trong run-remote-client.bat
run-remote-client.bat
```

## Lưu ý quan trọng:

1. **Remote EJB** chỉ chứa business logic đơn giản, không truy cập database
2. **Local EJB** được sử dụng cho database operations trong cùng JVM
3. **Remote EJB** có thể được gọi từ client ở máy khác qua mạng
4. JNDI name có thể thay đổi tùy theo version và cách deploy

## Troubleshooting:

Nếu Remote EJB lookup fail:
1. Check GlassFish admin console tại `http://localhost:4848`
2. Vào Applications > [your-app] > View JNDI names
3. Copy chính xác JNDI name và update code
4. Đảm bảo GlassFish đang chạy và app đã deploy thành công

## Architecture:

```
ejb-demo-ear (EAR)
├── ejb-demo-api (JAR)      # Remote & Local interfaces
├── ejb-demo-ejb (JAR)      # EJB implementations  
└── ejb-demo-war (WAR)      # Web layer + Remote client demos
```

Bây giờ dự án đã có đầy đủ demo cho cả 3 loại EJB: Session Bean (Local), Session Bean (Remote), và Message-Driven Bean!
