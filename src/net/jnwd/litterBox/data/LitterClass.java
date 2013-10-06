
package net.jnwd.litterBox.data;

import android.content.ContentValues;
import android.database.Cursor;

public class LitterClass {
    public static final String table = "litterClass";

    public static final String column_ID = "_id";
    public static final String column_Description = "description";

    public static final String[][] columns = {
            {
                    column_ID, "integer"
            },
            {
                    column_Description, "text"
            }
    };

    public static String[] allColumns = {
            column_ID,
            column_Description
    };

    public static final String showColumn = column_Description;

    private Long id;
    private String description;

    public LitterClass() {
        super();
    }

    @Override
    public String toString() {
        return "" +
                "Class: \n" +
                "id -> " + id + "\n" +
                "description -> " + description + "\n";
    }

    public LitterClass(String desc) {
        this();

        description = desc;
    }

    public LitterClass(Cursor cursor) {
        this();

        id = cursor.getLong(cursor.getColumnIndex(column_ID));
        description = cursor.getString(cursor.getColumnIndex(column_Description));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String createCommand() {
        String command = "Create table " + LitterClass.table + " (";

        for (String[] column : columns) {
            if (column[0].equalsIgnoreCase("_id")) {
                command += "_id integer PRIMARY KEY autoincrement";
            } else {
                command += ", " + column[0] + " " + column[1];
            }
        }

        command += ");";

        return command;
    }

    public ContentValues addNew() {
        ContentValues initialValues = new ContentValues();

        initialValues.put(columns[1][0], description);

        return initialValues;
    }
}
