import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CreateTable {

    public static void parseCreateString(String createString) {
        System.out.println("CREATE METHOD");
        System.out.println("Parsing the string:\"" + createString + "\"");
        String[] tokens = createString.split(" ");

        // Check if it's an index creation command
        if (tokens[1].compareTo("index") == 0) {
            String indexName = tokens[2]; // Index name is the second token
            String tableName = tokens[3]; // Table name is the fourth token
            String col = tokens[4];       // Column name is the fifth token
            String colName = col.substring(1, col.length() - 1); // Remove quotes from column name

            // Create the index
            Index.createIndex(tableName, indexName, colName, "String");
        } else {
            // Check if it's a table creation command
            if (tokens[1].compareTo("table") > 0) {
                System.out.println("Wrong syntax");
            } else {
                String tableName = tokens[2]; // Table name is the second token
                String[] temp = createString.split(tableName);
                String cols = temp[1].trim();
                String[] create_cols = cols.substring(1, cols.length() - 1).split(",");

                for (int i = 0; i < create_cols.length; i++)
                    create_cols[i] = create_cols[i].trim();

                // Check if the table already exists
                if (DavisBase.tableExists(tableName)) {
                    System.out.println("Table " + tableName + " already exists.");
                } else {
                    createTable(tableName, create_cols);
                }
            }
        }
    }

    public static void createTable(String table, String[] col) {
        try {
            // Create table file (.tbl)
            RandomAccessFile file = new RandomAccessFile("data/" + table + ".tbl", "rw");
            file.setLength(Table.pageSize);
            file.seek(0);
            file.writeByte(0x0D); // Write
            file.close();

            // Insert into davisbase_tables
            file = new RandomAccessFile("data/davisbase_tables.tbl", "rw");
            int numOfPages = Table.pages(file);
            int page = 1;
            for (int p = 1; p <= numOfPages; p++) {
                int rm = Page.getRightMost(file, p);
                if (rm == 0)
                    page = p;
            }

            int[] keys = Page.getKeyArray(file, page);
            int l = keys[0];
            for (int i = 0; i < keys.length; i++)
                if (keys[i] > l)
                    l = keys[i];
            file.close();

            String[] values = {Integer.toString(l + 1), table};
            insertInto("davisbase_tables", values);

            // Insert into davisbase_columns with rowid
            file = new RandomAccessFile("data/davisbase_columns.tbl", "rw");
            numOfPages = Table.pages(file);
            page = 1;
            for (int p = 1; p <= numOfPages; p++) {
                int rm = Page.getRightMost(file, p);
                if (rm == 0)
                    page = p;
            }

            keys = Page.getKeyArray(file, page);
            l = keys[0];
            for (int i = 0; i < keys.length; i++)
                if (keys[i] > l)
                    l = keys[i];
            file.close();

            // Add hidden rowid as the first column
            String[] rowidColumn = {"rowid INTEGER NOT NULL"};

            // Combine the rowid column with the user-defined columns
            String[] create_cols_with_rowid = concatenate(rowidColumn, col);

            // Process each column
            for (int i = 0; i < create_cols_with_rowid.length; i++) {
                l = l + 1;
                String[] token = create_cols_with_rowid[i].split(" ");
                String col_name = token[0];
                String dt = token[1].toUpperCase();
                String pos = Integer.toString(i + 1);
                String nullable = "YES";
                if (token.length > 2) {
                    nullable = "NO";
                }

                // Check for PRIMARY KEY constraint and uniqueness
                if (col_name.equals("rowid")) {
                    continue; // Skip rowid from primary key check
                }
                if (token.length == 3 && token[2].equals("PRIMARY") && i == 0) {
                    System.out.println("PRIMARY KEY constraint violation: only one column can be PRIMARY KEY.");
                    return;
                }

                String[] value = {Integer.toString(l), table, col_name, dt, pos, nullable};
                insertInto("davisbase_columns", value);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String[] concatenate(String[] a, String[] b) {
        String[] result = new String[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static void insertInto(String table, String[] values) {
        try {
            RandomAccessFile file = new RandomAccessFile("data/" + table + ".tbl", "rw");
            insertInto(file, table, values);
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertInto(RandomAccessFile file, String table, String[] values) {
        String[] dtype = Table.getDataType(table);
        String[] nullable = Table.getNullable(table);

        for (int i = 0; i < nullable.length; i++)
            if (values[i].equals("null") && nullable[i].equals("NO")) {
                System.out.println("NULL-value constraint violation");
                System.out.println();
                return;
            }

        int key = new Integer(values[0]);
        int page = Table.searchKeyPage(file, key);
        if (page != 0)
            if (Page.hasKey(file, page, key)) {
                System.out.println("Uniqueness constraint violation");
                return;
            }
        if (page == 0)
            page = 1;

        byte[] stc = new byte[dtype.length - 1];
        short plSize = (short) Table.calPayloadSize(table, values, stc);
        int cellSize = plSize + 6;
        int offset = Page.checkLeafSpace(file, page, cellSize);

        if (offset != -1) {
            Page.insertLeafCell(file, page, offset, plSize, key, stc, values);
        } else {
            Page.splitLeaf(file, page);
            insertInto(file, table, values);
        }
    }
}
