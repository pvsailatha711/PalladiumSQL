# PalladiumSQL

A lightweight, educational database management system (DBMS) built in Java, implementing core database functionalities with B+ tree indexing. This project is based on DavisBase and demonstrates fundamental database concepts including storage management, indexing, and SQL query processing.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technical Implementation](#technical-implementation)

## Overview

PalladiumSQL is a simplified relational database management system that implements essential DBMS features including:
- Custom storage engine with page-based file organization
- B+ tree indexing for efficient data retrieval
- SQL-like query language support
- Metadata management system
- CRUD operations (Create, Read, Update, Delete)

This project serves as an educational tool to understand the internal workings of database systems, from low-level storage management to high-level query processing.

## Features

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

## Architecture

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

## Usage

### Table Operations
- `CREATE TABLE`
- `DROP TABLE`
- `SHOW TABLES`

### Data Manipulation
- `INSERT INTO`
- `SELECT`
- `UPDATE`
- `DELETE`

### Index Operations
- `CREATE INDEX`
- `DROP INDEX`

### System Commands
- `HELP`
- `VERSION`
- `EXIT`

## Project Structure

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

## Technical Implementation

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
