
package net.jnwd.litterBox.data;

import android.content.ContentValues;
import android.database.Cursor;

public class LitterClassAttribute {
    public static final String table = "litterClassAttribute";

    public static final String column_ID = "_id";
    public static final String column_ParentID = "parentID";
    public static final String column_Sequence = "sequence";
    public static final String column_ClassID = "classID";
    public static final String column_AttributeID = "attributeID";
    public static final String column_Label = "label";

    public static final String[][] columns = {
            {
                    column_ID, "integer"
            },
            {
                    column_ParentID, "integer"
            },
            {
                    column_Sequence, "integer"
            },
            {
                    column_ClassID, "integer"
            },
            {
                    column_AttributeID, "integer"
            },
            {
                    column_Label, "text"
            }
    };

    public static String[] allColumns = {
            column_ID,
            column_ParentID,
            column_Sequence,
            column_ClassID,
            column_AttributeID,
            column_Label
    };

    public static final String showColumn = "description";

    private Long id;
    private Long parentID;
    private Long sequence;
    private Long classID;
    private Long attributeID;
    private String label;

    public LitterClassAttribute() {
        super();
    }

    public LitterClassAttribute(Long parentID, long sequence, Long classID, Long attributeID,
            String label) {
        this();

        this.parentID = parentID;
        this.sequence = sequence;
        this.classID = classID;
        this.attributeID = attributeID;
        this.label = label;
    }

    public LitterClassAttribute(Cursor cursor) {
        this();

        id = cursor.getLong(cursor.getColumnIndex(column_ID));
        parentID = cursor.getLong(cursor.getColumnIndex(column_ParentID));
        sequence = cursor.getLong(cursor.getColumnIndex(column_Sequence));
        classID = cursor.getLong(cursor.getColumnIndex(column_ClassID));
        attributeID = cursor.getLong(cursor.getColumnIndex(column_AttributeID));
        label = cursor.getString(cursor.getColumnIndex(column_Label));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getClassID() {
        return classID;
    }

    public void setClassID(Long classID) {
        this.classID = classID;
    }

    public Long getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(Long attributeID) {
        this.attributeID = attributeID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static String createCommand() {
        String command = "Create table " + LitterClassAttribute.table + " (";

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

        initialValues.put(columns[1][0], parentID);
        initialValues.put(columns[2][0], sequence);
        initialValues.put(columns[3][0], classID);
        initialValues.put(columns[4][0], attributeID);
        initialValues.put(columns[5][0], label);

        return initialValues;
    }
}
