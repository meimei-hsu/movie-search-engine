.PHONY: help clean build test run deploy undeploy install

# Variables
JAVA_HOME ?= $(shell /usr/libexec/java_home)
JAVAC := $(JAVA_HOME)/bin/javac
JAVA := $(JAVA_HOME)/bin/java
CATALINA_HOME ?= $(shell find /opt /usr/local -name "apache-tomcat*" -type d 2>/dev/null | head -n1)
SRC_DIR := src
BUILD_DIR := build/classes
WEBCONTENT_DIR := WebContent
LIB_DIR := $(WEBCONTENT_DIR)/WEB-INF/lib
CLASSPATH := $(BUILD_DIR):$(LIB_DIR)/*

# Color output
BLUE := \033[0;34m
GREEN := \033[0;32m
RED := \033[0;31m
NC := \033[0m # No Color

help: ## Display this help message
	@echo "$(BLUE)Movie Search Engine - Available Commands$(NC)"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "$(GREEN)%-20s$(NC) %s\n", $$1, $$2}'
	@echo ""
	@echo "$(BLUE)Example Usage:$(NC)"
	@echo "  make clean build    # Clean and rebuild"
	@echo "  make run            # Run the application"
	@echo "  make test           # Run unit tests"

clean: ## Remove all compiled files and build artifacts
	@echo "$(BLUE)Cleaning build artifacts...$(NC)"
	@rm -rf $(BUILD_DIR)
	@rm -f DS_Final_Project.war
	@mkdir -p $(BUILD_DIR)
	@echo "$(GREEN)✓ Clean complete$(NC)"

install: ## Ensure build directory exists
	@mkdir -p $(BUILD_DIR)
	@echo "$(GREEN)✓ Installation complete$(NC)"

build: clean install ## Compile all Java source files
	@echo "$(BLUE)Building project...$(NC)"
	@echo "Java Home: $(JAVA_HOME)"
	@echo "Classpath: $(CLASSPATH)"
	@$(JAVAC) -d $(BUILD_DIR) -cp $(CLASSPATH) $(SRC_DIR)/*.java
	@echo "$(GREEN)✓ Build successful$(NC)"
	@echo "Output directory: $(BUILD_DIR)"

war: build ## Create WAR file for deployment
	@echo "$(BLUE)Creating WAR file...$(NC)"
	@cd $(WEBCONTENT_DIR) && jar cvf ../DS_Final_Project.war .
	@echo "$(GREEN)✓ WAR file created: DS_Final_Project.war$(NC)"

test: build ## Run unit tests
	@echo "$(BLUE)Running tests...$(NC)"
	@$(JAVA) -cp $(CLASSPATH) UnitTest
	@echo "$(GREEN)✓ Tests complete$(NC)"

run: build ## Compile and run (requires Tomcat setup)
	@echo "$(BLUE)Starting application...$(NC)"
	@if [ -z "$(CATALINA_HOME)" ]; then \
		echo "$(RED)✗ CATALINA_HOME not set. Please set it to your Tomcat directory.$(NC)"; \
		echo "  Example: export CATALINA_HOME=/path/to/tomcat"; \
		exit 1; \
	fi
	@echo "Tomcat Home: $(CATALINA_HOME)"
	@echo "$(GREEN)Starting Tomcat...$(NC)"
	@$(CATALINA_HOME)/bin/startup.sh
	@echo "$(GREEN)✓ Tomcat started. Access at http://localhost:8080/DS_Final_Project/$(NC)"

stop: ## Stop Tomcat server
	@echo "$(BLUE)Stopping Tomcat...$(NC)"
	@if [ -z "$(CATALINA_HOME)" ]; then \
		echo "$(RED)✗ CATALINA_HOME not set.$(NC)"; \
		exit 1; \
	fi
	@$(CATALINA_HOME)/bin/shutdown.sh
	@echo "$(GREEN)✓ Tomcat stopped$(NC)"

deploy: war ## Deploy WAR file to Tomcat
	@echo "$(BLUE)Deploying application...$(NC)"
	@if [ -z "$(CATALINA_HOME)" ]; then \
		echo "$(RED)✗ CATALINA_HOME not set.$(NC)"; \
		exit 1; \
	fi
	@cp DS_Final_Project.war $(CATALINA_HOME)/webapps/
	@echo "$(GREEN)✓ Application deployed to $(CATALINA_HOME)/webapps/$(NC)"

undeploy: ## Remove deployed application from Tomcat
	@echo "$(BLUE)Undeploying application...$(NC)"
	@if [ -z "$(CATALINA_HOME)" ]; then \
		echo "$(RED)✗ CATALINA_HOME not set.$(NC)"; \
		exit 1; \
	fi
	@rm -rf $(CATALINA_HOME)/webapps/DS_Final_Project*
	@echo "$(GREEN)✓ Application undeployed$(NC)"

logs: ## Display Tomcat logs
	@if [ -z "$(CATALINA_HOME)" ]; then \
		echo "$(RED)✗ CATALINA_HOME not set.$(NC)"; \
		exit 1; \
	fi
	@tail -f $(CATALINA_HOME)/logs/catalina.out

check-java: ## Verify Java installation
	@echo "$(BLUE)Java Configuration:$(NC)"
	@echo "Java Home: $(JAVA_HOME)"
	@$(JAVAC) -version
	@$(JAVA) -version

check-tomcat: ## Verify Tomcat installation
	@echo "$(BLUE)Tomcat Configuration:$(NC)"
	@if [ -z "$(CATALINA_HOME)" ]; then \
		echo "$(RED)✗ CATALINA_HOME not set.$(NC)"; \
		exit 1; \
	fi
	@echo "Tomcat Home: $(CATALINA_HOME)"
	@ls -la $(CATALINA_HOME)/bin/startup.sh

check-env: check-java check-tomcat ## Check all environment configurations

.DEFAULT_GOAL := help
