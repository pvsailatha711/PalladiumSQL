# PalladiumSQL

A lightweight, educational database management system (DBMS) built in Java, implementing core database functionalities with B+ tree indexing. This project is based on DavisBase and demonstrates fundamental database concepts including storage management, indexing, and SQL query processing.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Usage](#usage)
  - [Supported Commands](#supported-commands)
  - [Examples](#examples)
- [Project Structure](#project-structure)
- [Technical Implementation](#technical-implementation)
- [Team](#team)
- [License](#license)

## 🎯 Overview

PalladiumSQL is a simplified relational database management system that implements essential DBMS features including:
- Custom storage engine with page-based file organization
- B+ tree indexing for efficient data retrieval
- SQL-like query language support
- Metadata management system
- CRUD operations (Create, Read, Update, Delete)

This project serves as an educational tool to understand the internal workings of database systems, from low-level storage management to high-level query processing.

## ✨ Features

### Core Functionality
- **Table Management**: Create, drop, and manage database tables
- **Data Manipulation**: Insert, update, delete, and query records
- **Indexing**: B+ tree implementation for optimized data access
- **Query Processing**: Support for SELECT queries with WHERE clauses
- **Metadata System**: Built-in system tables for schema management
- **Page-Based Storage**: Efficient 512-byte page organization

### SQL Support
- `CREATE TABLE` - Define new tables with typed columns
- `CREATE INDEX` - Build indexes on table columns
- `INSERT INTO` - Add new records
- `SELECT` - Query data with conditional filtering
- `UPDATE` - Modify existing records
- `DELETE` - Remove records
- `DROP TABLE` - Delete tables
- `DROP INDEX` - Remove indexes
- `SHOW TABLES` - List all tables

## 🏗️ Architecture

### Storage Layer
- **Page Size**: 512 bytes per page
- **File Format**: Binary `.tbl` files for tables, `.ndx` files for indexes
- **Page Types**: Interior pages and leaf pages for B+ tree structure

### System Tables
- `davisbase_tables` - Stores metadata about user tables
- `davisbase_columns` - Stores column information and data types
- `davisbase_indexes` - Manages index metadata

### Key Components

| Component | Description |
|-----------|-------------|
| `DavisBase.java` | Main entry point and command parser |
| `BPlusTreeImpl.java` | B+ tree implementation for indexing |
| `Page.java` | Page-level operations and management |
| `Table.java` | Table-level operations |
| `CreateTable.java` | Table creation logic |
| `Insert.java` | Record insertion handler |
| `UpdateTable.java` | Record update handler |
| `DeleteTable.java` | Record deletion handler |
| `Index.java` | Index creation and management |

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Command line terminal (Windows PowerShell, CMD, or Unix terminal)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/pvsailatha711/PalladiumSQL.git
   cd PalladiumSQL
   ```

2. **Compile the Java files**
   ```bash
   javac *.java
   ```

### Running the Application

```bash
java DavisBase
```

You should see the welcome screen:
```
--------------------------------------------------------------------------------
Welcome to DavisBaseLite
DavisBase Version v1.3
Team Palladium

Type "help;" to display supported commands.
--------------------------------------------------------------------------------
PalladiumSql>
```

## 💻 Usage

### Supported Commands

#### Table Operations
```sql
-- Create a new table
CREATE TABLE table_name (column1 datatype, column2 datatype, ...);

-- Show all tables
SHOW TABLES;

-- Drop a table
DROP TABLE table_name;
```

#### Data Manipulation
```sql
-- Insert a record
INSERT INTO table_name VALUES (value1, value2, ...);

-- Select all records
SELECT * FROM table_name;

-- Select with condition
SELECT * FROM table_name WHERE column_name = value;

-- Update records
UPDATE table_name SET column_name = value WHERE condition;

-- Delete records
DELETE FROM table_name WHERE condition;
```

#### Index Operations
```sql
-- Create an index
CREATE INDEX index_name ON table_name (column_name);

-- Drop an index
DROP INDEX index_name;
```

#### System Commands
```sql
-- Display help
HELP;

-- Show version
VERSION;

-- Exit the program
EXIT;
```

### Examples

#### Creating a Table
```sql
PalladiumSql> CREATE TABLE students (id INT, name TEXT, age INT, gpa REAL);
```

#### Inserting Data
```sql
PalladiumSql> INSERT INTO students VALUES (1, 'John Doe', 20, 3.5);
PalladiumSql> INSERT INTO students VALUES (2, 'Jane Smith', 21, 3.8);
```

#### Querying Data
```sql
-- Select all records
PalladiumSql> SELECT * FROM students;

-- Select with condition
PalladiumSql> SELECT * FROM students WHERE age > 20;
```

#### Creating an Index
```sql
PalladiumSql> CREATE INDEX idx_student_id ON students (id);
```

#### Updating Records
```sql
PalladiumSql> UPDATE students SET gpa = 3.9 WHERE id = 2;
```

#### Deleting Records
```sql
PalladiumSql> DELETE FROM students WHERE id = 1;
```

## 📁 Project Structure

```
PalladiumSQL/
├── data/                          # Database files directory
│   ├── davisbase_tables.tbl      # System table for table metadata
│   ├── davisbase_columns.tbl     # System table for column metadata
│   └── *.tbl                     # User table files
│   └── *.ndx                     # Index files
├── BPlusTreeImpl.java            # B+ tree implementation
├── BTreeImpl.java                # B-tree base implementation
├── Buffer.java                   # Buffer management
├── Constants.java                # System constants
├── CreateTable.java              # Table creation logic
├── DavisBase.java                # Main application entry point
├── DeleteTable.java              # Record deletion handler
├── DropTable.java                # Table drop handler
├── Index.java                    # Index management
├── Init.java                     # System initialization
├── Insert.java                   # Record insertion handler
├── Page.java                     # Page-level operations
├── ShowTables.java               # Table listing and SELECT queries
├── Table.java                    # Table-level operations
├── UpdateTable.java              # Record update handler
└── README.md                     # This file
```

## 🔧 Technical Implementation

### Data Types Supported
- `INT` - Integer values
- `TINYINT` - Small integer values
- `SMALLINT` - Short integer values
- `BIGINT` - Large integer values
- `REAL` - Floating-point numbers
- `DOUBLE` - Double-precision floating-point
- `DATE` - Date values
- `DATETIME` - Date and time values
- `TEXT` - Variable-length strings

### B+ Tree Indexing
The system implements a B+ tree structure for efficient data retrieval:
- **Interior Nodes**: Store keys and pointers to child pages
- **Leaf Nodes**: Store actual data records
- **Page Splitting**: Automatic page splitting when capacity is exceeded
- **Sorted Storage**: Keys are maintained in sorted order for binary search

### Storage Format
- **Page Header**: Contains metadata (page type, cell count, content offset)
- **Cell Pointers**: Array of pointers to cell locations
- **Cells**: Actual data storage units containing records
- **Free Space**: Unallocated space for new cells

### Query Processing
1. **Parsing**: SQL commands are tokenized and parsed
2. **Validation**: Schema and constraint validation
3. **Execution**: Operation execution using B+ tree navigation
4. **Result**: Formatted output to console

## 👥 Team

**Team Palladium**

This project was developed as part of a Database Design course at the University of Texas at Dallas.

## 📄 License

This project is based on DavisBase by Chris Irwin Davis.

Original Copyright © 2016 Chris Irwin Davis

---

## 🤝 Contributing

This is an educational project. If you'd like to contribute improvements or bug fixes:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/improvement`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/improvement`)
5. Create a Pull Request

## 📞 Contact

For questions or feedback, please open an issue on the GitHub repository.

---

**Note**: This is an educational project designed to demonstrate database concepts. It is not intended for production use.
