# Setup Guide - Movie Search Engine

This guide provides step-by-step instructions for setting up the project on different operating systems.

## Table of Contents

- [macOS Setup](#macos-setup)
- [Linux Setup](#linux-setup)
- [Windows Setup](#windows-setup)
- [Docker Setup (Optional)](#docker-setup-optional)
- [Troubleshooting](#troubleshooting)

## macOS Setup

### 1. Install Java Development Kit (JDK)

#### Option A: Using Homebrew (Recommended)

```bash
# Install Homebrew if not already installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java 8 or 11
brew install openjdk@11

# Set JAVA_HOME
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 11)' >> ~/.zshrc
source ~/.zshrc

# Verify installation
java -version
javac -version
```

#### Option B: Download from Oracle

1. Visit [Oracle JDK Download](https://www.oracle.com/java/technologies/downloads/)
2. Download JDK 8 or 11 for macOS
3. Run the installer
4. Set `JAVA_HOME`:

```bash
echo 'export JAVA_HOME=$(/usr/libexec/java_home)' >> ~/.zshrc
source ~/.zshrc
```

### 2. Install Apache Tomcat

```bash
# Using Homebrew
brew install tomcat

# Set CATALINA_HOME
echo 'export CATALINA_HOME=/usr/local/opt/tomcat/libexec' >> ~/.zshrc
source ~/.zshrc

# Verify installation
$CATALINA_HOME/bin/startup.sh
# Open http://localhost:8080
$CATALINA_HOME/bin/shutdown.sh
```

### 3. Clone and Build the Project

```bash
# Clone repository
git clone https://github.com/meimei-hsu/DS_Final_Project.git
cd DS_Final_Project

# Build project
make clean build

# Run tests
make test

# Deploy and run
make run
```

---

## Linux Setup

### 1. Install Java Development Kit (JDK)

#### Ubuntu/Debian

```bash
# Update package list
sudo apt update

# Install OpenJDK 11
sudo apt install -y openjdk-11-jdk

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc

# Verify installation
java -version
javac -version
```

#### Fedora/CentOS/RHEL

```bash
# Install OpenJDK 11
sudo yum install -y java-11-openjdk-devel

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk' >> ~/.bashrc
source ~/.bashrc

# Verify installation
java -version
```

### 2. Install Apache Tomcat

```bash
# Create tomcat user
sudo useradd -r -m -U -d /opt/tomcat -s /sbin/nologin tomcat

# Download Tomcat
cd /tmp
wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.72/bin/apache-tomcat-9.0.72.tar.gz

# Extract and move
tar xzf apache-tomcat-9.0.72.tar.gz
sudo mv apache-tomcat-9.0.72 /opt/tomcat

# Set permissions
sudo chown -R tomcat: /opt/tomcat

# Set CATALINA_HOME
echo 'export CATALINA_HOME=/opt/tomcat' >> ~/.bashrc
source ~/.bashrc

# Start Tomcat
$CATALINA_HOME/bin/startup.sh
```

### 3. Clone and Build the Project

```bash
# Install git if needed
sudo apt install -y git

# Clone repository
git clone https://github.com/meimei-hsu/DS_Final_Project.git
cd DS_Final_Project

# Build and run
make clean build
make test
make run
```

---

## Windows Setup

### 1. Install Java Development Kit (JDK)

#### Option A: Using Chocolatey (Recommended)

```powershell
# Install Chocolatey if not already installed
# Run PowerShell as Administrator and execute:
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# Install Java
choco install openjdk11

# Verify installation
java -version
javac -version
```

#### Option B: Manual Download

1. Visit [Oracle JDK Download](https://www.oracle.com/java/technologies/downloads/)
2. Download JDK 8 or 11 for Windows (x64)
3. Run the installer
4. Set JAVA_HOME environment variable:
   - Right-click "This PC" or "My Computer"
   - Click "Properties"
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Add new System variable:
     - Name: `JAVA_HOME`
     - Value: `C:\Program Files\Java\jdk-11` (or your installation path)

### 2. Install Apache Tomcat

#### Option A: Using Chocolatey

```powershell
choco install tomcat

# Set CATALINA_HOME (usually auto-set)
# Default: C:\Program Files\Apache Software Foundation\Tomcat 9.0
```

#### Option B: Manual Download

1. Download from [Apache Tomcat](https://tomcat.apache.org/)
2. Extract to `C:\Program Files\Tomcat`
3. Set environment variable:
   - Name: `CATALINA_HOME`
   - Value: `C:\Program Files\Tomcat`

### 3. Clone and Build the Project

```powershell
# Install Git (if not already installed)
# Download from https://git-scm.com/download/win

# Clone repository
git clone https://github.com/meimei-hsu/DS_Final_Project.git
cd DS_Final_Project

# Build using provided batch files (if available)
# Or use Makefile with GNU Make (install via Chocolatey)
choco install make

# Build and test
make clean build
make test
make run
```

---

## Docker Setup (Optional)

### Prerequisites

- Docker installed ([Installation Guide](https://docs.docker.com/get-docker/))

### Using Docker Compose

Create a `docker-compose.yml` in the project root:

```yaml
version: '3.8'

services:
  tomcat:
    image: tomcat:9-jdk11
    ports:
      - "8080:8080"
    volumes:
      - ./WebContent:/usr/local/tomcat/webapps/DS_Final_Project
      - ./build/classes:/usr/local/tomcat/webapps/DS_Final_Project/WEB-INF/classes
    environment:
      - JAVA_OPTS=-Xmx1024m -Xms512m
    networks:
      - app-network

networks:
  app-network:
    driver: bridge
```

### Running with Docker

```bash
# Build and start containers
docker-compose up --build

# Access application at http://localhost:8080/DS_Final_Project/

# Stop containers
docker-compose down
```

### Building Docker Image Manually

Create `Dockerfile`:

```dockerfile
FROM tomcat:9-jdk11

# Copy WebContent
COPY WebContent/ /usr/local/tomcat/webapps/DS_Final_Project/

# Copy compiled classes
COPY build/classes/ /usr/local/tomcat/webapps/DS_Final_Project/WEB-INF/classes/

# Copy dependencies
COPY WebContent/WEB-INF/lib/ /usr/local/tomcat/webapps/DS_Final_Project/WEB-INF/lib/

EXPOSE 8080
```

Build and run:

```bash
# Build image
docker build -t movie-search-engine .

# Run container
docker run -p 8080:8080 movie-search-engine

# Access at http://localhost:8080/DS_Final_Project/
```

---

## Troubleshooting

### Common Issues

#### 1. JAVA_HOME Not Set

**Problem**: `The tool simplified the command to...`

**Solution**:

```bash
# macOS/Linux: Add to ~/.bashrc or ~/.zshrc
export JAVA_HOME=$(/usr/libexec/java_home)

# Windows: Set environment variable
# See Windows Setup section above
```

#### 2. Port 8080 Already in Use

**macOS/Linux**:

```bash
# Find process using port 8080
lsof -i :8080

# Kill process
kill -9 <PID>

# Or change Tomcat port in conf/server.xml
```

**Windows**:

```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process
taskkill /PID <PID> /F

# Or change Tomcat port in conf/server.xml
```

#### 3. Compilation Errors

```bash
# Verify Java version compatibility
java -version

# Clear build directory and rebuild
make clean
make build

# Check classpath includes all JARs
ls -la WebContent/WEB-INF/lib/
```

#### 4. Tomcat Won't Start

```bash
# Check Tomcat logs
tail -f $CATALINA_HOME/logs/catalina.out

# Ensure CATALINA_HOME is set correctly
echo $CATALINA_HOME

# Try manual startup
cd $CATALINA_HOME/bin
./startup.sh
```

#### 5. Application Not Accessible

- Ensure Tomcat is running: `http://localhost:8080`
- Check application deployment: `http://localhost:8080/DS_Final_Project/`
- Check Tomcat logs for errors
- Verify WAR file in `$CATALINA_HOME/webapps/`

### Getting Help

1. Check [README.md](README.md) Troubleshooting section
2. Review logs: `$CATALINA_HOME/logs/catalina.out`
3. Search existing [GitHub Issues](https://github.com/meimei-hsu/DS_Final_Project/issues)
4. Create a new issue with:
   - OS and version
   - Java version: `java -version`
   - Error messages and logs
   - Steps to reproduce

---

## Next Steps

After successful installation:

1. **Access the Application**: Open `http://localhost:8080/DS_Final_Project/`
2. **Run Tests**: `make test`
3. **Read Documentation**: See [README.md](README.md)
4. **Contribute**: See [CONTRIBUTING.md](CONTRIBUTING.md)

Happy coding! 🎬
