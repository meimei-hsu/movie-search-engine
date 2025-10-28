#!/bin/bash

################################################################################
# Movie Search Engine - Application Runner Script
# This script automates setup, compilation, and deployment
################################################################################

set -e  # Exit on error

# Color codes for output
BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAVA_HOME="${JAVA_HOME:-$(/usr/libexec/java_home)}"
CATALINA_HOME="${CATALINA_HOME:-}"
SRC_DIR="${PROJECT_DIR}/src"
BUILD_DIR="${PROJECT_DIR}/build/classes"
WEBCONTENT_DIR="${PROJECT_DIR}/WebContent"
LIB_DIR="${WEBCONTENT_DIR}/WEB-INF/lib"

################################################################################
# Functions
################################################################################

print_header() {
    echo -e "${BLUE}════════════════════════════════════════════════════════════${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}════════════════════════════════════════════════════════════${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

check_java() {
    print_header "Checking Java Installation"
    
    if [ -z "$JAVA_HOME" ]; then
        print_error "JAVA_HOME is not set"
        echo "Please set JAVA_HOME or ensure Java is installed"
        exit 1
    fi
    
    if [ ! -d "$JAVA_HOME" ]; then
        print_error "JAVA_HOME points to invalid directory: $JAVA_HOME"
        exit 1
    fi
    
    JAVAC="${JAVA_HOME}/bin/javac"
    JAVA="${JAVA_HOME}/bin/java"
    
    if [ ! -x "$JAVAC" ]; then
        print_error "javac not found at $JAVAC"
        exit 1
    fi
    
    java_version=$("$JAVAC" -version 2>&1 | head -n1)
    print_success "Java found: $java_version"
    print_info "JAVA_HOME: $JAVA_HOME"
}

check_classpath() {
    print_header "Verifying Dependencies"
    
    if [ ! -d "$LIB_DIR" ]; then
        print_error "Library directory not found: $LIB_DIR"
        exit 1
    fi
    
    jar_count=$(find "$LIB_DIR" -name "*.jar" 2>/dev/null | wc -l)
    
    if [ "$jar_count" -eq 0 ]; then
        print_warning "No JAR files found in $LIB_DIR"
        print_warning "Some dependencies may be missing"
    else
        print_success "Found $jar_count JAR dependencies"
        print_info "Dependencies:"
        find "$LIB_DIR" -name "*.jar" -exec basename {} \; | sed 's/^/  - /'
    fi
}

clean_build() {
    print_header "Cleaning Previous Build"
    
    if [ -d "$BUILD_DIR" ]; then
        rm -rf "$BUILD_DIR"
        print_success "Removed build directory"
    fi
    
    rm -f "${PROJECT_DIR}/DS_Final_Project.war"
    print_success "Cleaned build artifacts"
}

build_project() {
    print_header "Compiling Project"
    
    mkdir -p "$BUILD_DIR"
    
    CLASSPATH="${BUILD_DIR}:${LIB_DIR}/*"
    
    print_info "Compiling from: $SRC_DIR"
    print_info "Output to: $BUILD_DIR"
    print_info "Classpath: $CLASSPATH"
    
    if "$JAVAC" -d "$BUILD_DIR" -cp "$CLASSPATH" "$SRC_DIR"/*.java 2>&1; then
        print_success "Compilation successful"
        print_info "Compiled classes:"
        find "$BUILD_DIR" -name "*.class" -exec basename {} \; | sed 's/^/  - /'
    else
        print_error "Compilation failed"
        exit 1
    fi
}

run_tests() {
    print_header "Running Unit Tests"
    
    CLASSPATH="${BUILD_DIR}:${LIB_DIR}/*"
    
    print_info "Executing: UnitTest"
    
    if "$JAVA" -cp "$CLASSPATH" UnitTest; then
        print_success "Tests passed"
    else
        print_warning "Tests failed or completed with errors"
    fi
}

create_war() {
    print_header "Creating WAR File"
    
    if [ ! -d "$WEBCONTENT_DIR" ]; then
        print_error "WebContent directory not found"
        exit 1
    fi
    
    cd "$WEBCONTENT_DIR" || exit 1
    
    if jar cvf "${PROJECT_DIR}/DS_Final_Project.war" . > /dev/null 2>&1; then
        print_success "WAR file created: DS_Final_Project.war"
        print_info "Size: $(du -h "${PROJECT_DIR}/DS_Final_Project.war" | cut -f1)"
    else
        print_error "Failed to create WAR file"
        exit 1
    fi
    
    cd "$PROJECT_DIR" || exit 1
}

check_tomcat() {
    print_header "Checking Tomcat Configuration"
    
    if [ -z "$CATALINA_HOME" ]; then
        # Try to find Tomcat automatically
        CATALINA_HOME=$(find /opt /usr/local -name "apache-tomcat*" -type d 2>/dev/null | head -n1)
        
        if [ -z "$CATALINA_HOME" ]; then
            print_error "CATALINA_HOME not set and Tomcat not found"
            echo ""
            echo "To deploy, please set CATALINA_HOME:"
            echo "  export CATALINA_HOME=/path/to/tomcat"
            echo ""
            echo "Or install Tomcat:"
            echo "  macOS: brew install tomcat"
            echo "  Linux: sudo apt-get install tomcat9"
            echo "  Windows: Download from https://tomcat.apache.org/"
            return 1
        fi
    fi
    
    if [ ! -d "$CATALINA_HOME" ]; then
        print_error "CATALINA_HOME points to invalid directory: $CATALINA_HOME"
        return 1
    fi
    
    if [ ! -x "$CATALINA_HOME/bin/startup.sh" ]; then
        print_error "startup.sh not found or not executable"
        return 1
    fi
    
    print_success "Tomcat found"
    print_info "CATALINA_HOME: $CATALINA_HOME"
    
    return 0
}

deploy_application() {
    print_header "Deploying Application"
    
    if ! check_tomcat; then
        print_warning "Skipping deployment (Tomcat not configured)"
        return 1
    fi
    
    # Remove old deployment
    if [ -d "$CATALINA_HOME/webapps/DS_Final_Project" ]; then
        print_info "Removing previous deployment..."
        rm -rf "$CATALINA_HOME/webapps/DS_Final_Project"*
    fi
    
    # Copy WAR file
    cp "${PROJECT_DIR}/DS_Final_Project.war" "$CATALINA_HOME/webapps/"
    print_success "Application deployed to $CATALINA_HOME/webapps/"
}

start_tomcat() {
    print_header "Starting Tomcat Server"
    
    if ! check_tomcat; then
        print_error "Cannot start Tomcat"
        exit 1
    fi
    
    # Check if already running
    if pgrep -f "catalina" > /dev/null; then
        print_warning "Tomcat appears to be already running"
        print_info "Access application at: http://localhost:8080/DS_Final_Project/"
        return 0
    fi
    
    print_info "Starting Tomcat..."
    "$CATALINA_HOME/bin/startup.sh"
    
    # Wait for startup
    sleep 3
    
    # Check if started successfully
    if pgrep -f "catalina" > /dev/null; then
        print_success "Tomcat started successfully"
        print_info ""
        print_info "Application URL: http://localhost:8080/DS_Final_Project/"
        print_info "Tomcat logs: $CATALINA_HOME/logs/catalina.out"
        print_info ""
        print_info "To stop Tomcat: $CATALINA_HOME/bin/shutdown.sh"
        return 0
    else
        print_error "Tomcat failed to start"
        echo "Check logs at: $CATALINA_HOME/logs/catalina.out"
        exit 1
    fi
}

show_usage() {
    echo ""
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  build          - Compile the project only (no deployment)"
    echo "  test           - Run unit tests"
    echo "  deploy         - Build, create WAR, and deploy to Tomcat"
    echo "  run            - Full setup: build, test, deploy, and start Tomcat"
    echo "  help           - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 build       # Just compile"
    echo "  $0 run         # Full deployment (recommended)"
    echo ""
}

################################################################################
# Main Script
################################################################################

main() {
    print_header "Movie Search Engine - Setup & Deployment"
    
    # Parse command line arguments
    COMMAND="${1:-run}"
    
    case "$COMMAND" in
        build)
            check_java
            check_classpath
            clean_build
            build_project
            print_success "Build complete!"
            ;;
        test)
            check_java
            check_classpath
            build_project
            run_tests
            ;;
        deploy)
            check_java
            check_classpath
            clean_build
            build_project
            create_war
            deploy_application
            print_success "Deployment complete!"
            ;;
        run)
            check_java
            check_classpath
            clean_build
            build_project
            run_tests
            create_war
            deploy_application
            start_tomcat
            ;;
        help|--help|-h)
            show_usage
            ;;
        *)
            print_error "Unknown command: $COMMAND"
            show_usage
            exit 1
            ;;
    esac
    
    echo ""
}

# Run main function
main "$@"
