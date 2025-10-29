# Movie Search Engine - Data Structure Final Project

A specialized search engine that prioritizes movie-related results using a weighted keyword system. This project demonstrates advanced data structures, web scraping, and ranking algorithms to deliver more relevant movie content from Google search results.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Project Details](#project-details)

## Overview

### Motivation

Google's search results are not specialized for movie enthusiasts. This project creates a prioritizing system that re-ranks Google search results to emphasize movie-related content, providing a better search experience for users looking for movie information.

### Core Functionality

- **Smart Keyword Matching**: Searches for movie-related results regardless of input keywords
- **Multi-Language Support**: Supports Chinese (ZH), English (EN), Japanese (JP), and Korean (KR)
- **Result Re-ranking**: Sorts results into categories and displays the most relevant content first
- **Web-based Interface**: User-friendly JSP pages for search input and result display

## Features

✓ Weighted keyword system for result prioritization  
✓ Multi-language support (ZH, EN, JP, KR)  
✓ HTML parsing and URL extraction from Google search  
✓ Real-time result ranking and display  
✓ Error handling for network requests (HTTP 400, 403, 404)  
✓ Unit tests for core functionality  

## Project Structure

```
movie-search-engine/
├── src/                          # Java source files
│   ├── Main.java                 # Servlet dispatcher & main logic
│   ├── GoogleQuery.java          # Google API interaction & HTML parsing
│   ├── CalcScore.java            # Result ranking algorithm
│   ├── KeywordList.java          # Keyword database management
│   ├── Keyword.java              # Keyword model (name, weight)
│   └── UnitTest.java             # Unit tests
├── WebContent/                   # Web resources
│   ├── Search.jsp                # Search input page
│   ├── ResultPage.jsp            # Results display page
│   ├── META-INF/
│   │   └── MANIFEST.MF
│   ├── WEB-INF/
│   │   ├── web.xml               # Servlet configuration
│   │   └── lib/                  # External JAR dependencies
│   ├── wsdl/                     # Web service definitions
│   └── images/
├── build/                        # Compiled classes (auto-generated)
├── .classpath                    # Eclipse classpath configuration
├── .project                      # Eclipse project configuration
├── .settings/                    # Eclipse IDE settings
├── .gitignore                    # Git ignore rules
├── Makefile                      # Build automation
└── README.md                     # This file
```

## Prerequisites

- **Java Development Kit (JDK)**: 8 or higher
- **Apache Tomcat**: 8.0 or higher (for running the web application)
- **Apache Maven**: 3.6+ (optional, for dependency management)
- **Git**: For version control

### System Requirements

- Minimum 2GB RAM
- 500MB free disk space
- Internet connection (for Google search queries)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/meimei-hsu/movie-search-engine.git
cd movie-search-engine
```

### 2. Set Up Java Environment

Ensure JAVA_HOME is set:

```bash
# macOS/Linux
export JAVA_HOME=$(/usr/libexec/java_home)
echo $JAVA_HOME

# Windows
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_version
```

### 3. Install Dependencies

The project uses JAR files stored in `WebContent/WEB-INF/lib/`. No additional Maven setup is required, but ensure all dependencies are present:

- jsoup (for HTML parsing)
- Servlet API 3.0+
- JSP API 2.0+

## Building the Project

### Using the Makefile (Recommended)

```bash
# Clean and build
make clean
make build

# View all available commands
make help
```

### Manual Compilation

```bash
# Compile Java sources
javac -d build/classes -cp WebContent/WEB-INF/lib/* src/*.java

# Create WAR file (optional)
jar cvf movie-search-engine.war -C WebContent .
```

## Running the Application

### Option 1: Using Makefile

```bash
make run
```

This will:
1. Clean previous builds
2. Compile the project
3. Deploy to Tomcat (if configured)
4. Start the application

### Option 2: Using run.sh

```bash
chmod +x run.sh
./run.sh run
```

### Option 3: Manual Deployment

```bash
# 1. Start Tomcat (assuming CATALINA_HOME is set)
$CATALINA_HOME/bin/startup.sh

# 2. Copy WAR file to Tomcat webapps
cp movie-search-engine.war $CATALINA_HOME/webapps/

# 3. Access the application
# Open browser to http://localhost:8080/movie-search-engine/Search.jsp
```

## Usage

### Accessing the Application

1. Open your web browser
2. Navigate to `http://localhost:8080/movie-search-engine/`
3. You should see the search page (`Search.jsp`)

### Performing a Search

1. Enter a keyword in the search box
2. Select a language (ZH, EN, JP, KR)
3. Click "Search"
4. Results are displayed on `ResultPage.jsp` in prioritized order

### Understanding Results

Results are ranked based on:
- **Weight Score**: Higher = more movie-related
- **Keyword Matching**: Movies (highest), Movie-related (middle), Related content (lowest)
- **Content Relevance**: Matching content within the page

## Troubleshooting

### Common Issues

#### 1. Server Response 400 (Bad Request)

**Problem**: URLs from Google search contain special encoding  
**Solution**: The project includes URL formatting logic in `GoogleQuery.java`:

```java
if (citeUrl.startsWith("/url?q=")) {
    citeUrl = citeUrl.replace("/url?q=", "");
}
String[] splittedString = citeUrl.split("&sa=");
if (splittedString.length > 1) {
    citeUrl = splittedString[0];
}
citeUrl = java.net.URLDecoder.decode(citeUrl, StandardCharsets.UTF_8);
citeUrl = citeUrl.replaceAll(" ", "%20");
```

#### 2. Server Response 403/404 (Forbidden/Not Found)

**Problem**: Some URLs may be blocked or unreachable  
**Solution**: Error handling is implemented to skip problematic URLs:

```java
if (conn.getResponseCode() == 403 || conn.getResponseCode() == 400 || 
    conn.getResponseCode() == 404) {
    System.out.printf("Error %d: Skipping URL\n", conn.getResponseCode());
    // Continue with next result
}
```

#### 3. ClassNotFoundException

**Ensure all dependencies are in `WebContent/WEB-INF/lib/`:**
```bash
ls -la WebContent/WEB-INF/lib/
```

#### 4. Port 8080 Already in Use

```bash
# Kill process on port 8080 (macOS/Linux)
lsof -ti:8080 | xargs kill -9

# Or change Tomcat port in conf/server.xml
```

### Running Tests

```bash
# Using Makefile
make test

# Or directly
javac -d build/classes -cp WebContent/WEB-INF/lib/* src/UnitTest.java
java -cp build/classes:WebContent/WEB-INF/lib/* UnitTest
```

## Project Details

### System Design Logic

#### 1. Keyword Setting
- **Highest Weight**: Words directly meaning "movie"
- **Middle Weight**: Words related to movies
- **Lowest Weight**: Contextual movie-related terms

#### 2. Class Architecture

| Class | Purpose |
|-------|---------|
| `Keyword` | Model for keyword + weight pair |
| `KeywordList` | Database of preset keywords for evaluation |
| `GoogleQuery` | Handles Google API calls and HTML parsing |
| `CalcScore` | Implements ranking algorithm |
| `Main` | Servlet dispatcher connecting all components |
| `Search.jsp` | User interface for search input |
| `ResultPage.jsp` | Displays ranked results |

### Algorithm Overview

```
1. User submits search query
   ↓
2. GoogleQuery sends request to Google
   ↓
3. Parse HTML and extract URLs
   ↓
4. For each result:
   a. Fetch webpage content
   b. Count keyword matches from KeywordList
   c. Calculate weighted score in CalcScore
   ↓
5. Sort results by score
   ↓
6. Display in ResultPage.jsp
```

### Known Limitations

- Depends on Google search availability
- May encounter rate limiting with high-frequency queries
- HTML parsing may break with Google UI changes
- Limited to 4 languages currently

### Test Plans
We have complete the test plans below and fixed some problems listed above.

√ **Walk-through: **
	fixed some typing errors during this process

√ **Desk checking: **
	be more clear about our design logic during this process

√ **Unit testing: **
	test the methods in `GoogleQuery` and encountered some IO Exception problems

√ **Integration testing: **
	run the whole project on the server and no problems are discovered

√ **Performance test: **
	examine the response time for the proper amount of searching results → the best quality is to run 30 results in one go

√ **Alpha test: **
	use various search keywords to simulate the usage scenarios
