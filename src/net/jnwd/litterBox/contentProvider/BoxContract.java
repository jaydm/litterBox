
package net.jnwd.litterBox.contentProvider;

import android.content.ContentResolver;
import android.net.Uri;

public final class BoxContract {
    public static final String Db_Name = "litterBox";
    public static final int Db_Version = 1;

    public static final String Authority = "net.jnwd.litterBox.contentProvider";
    public static final Uri Content_Uri = Uri.parse("content://" + Authority);

    public static final class Attribute {
        public static final Uri Content_Uri = Uri.withAppendedPath(BoxContract.Content_Uri,
                "attributes");

        public static final String Content_Type = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/box_attributes";
        public static final String Content_Item_Type = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/box_attributes";

        public static final String table = "litterAttribute";

        public static final String column_ID = "_id";
        public static final String column_Description = "description";
        public static final String column_Type = "type";

        public static final String[][] columns = {
                {
                        column_ID, "integer"
                },
                {
                        column_Description, "text"
                },
                {
                        column_Type, "text"
                }
        };

        public static final String[] allColumns = {
                column_ID,
                column_Description,
                column_Type
        };

        public static final String showColumn = column_Description;

        public static final String defaultSortOrder = column_Description;

        public static String createCommand() {
            String command = "Create table " + table + " (";

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
    }

    public static final class AttributeValue {
        public static final Uri Content_Uri = Uri.withAppendedPath(BoxContract.Content_Uri,
                "attributeValues");

        public static final String Content_Type = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/box_attribute_values";
        public static final String Content_Item_Type = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/box_attribute_values";

        public static final String table = "litterAttributeValue";

        public static final String column_ID = "_id";
        public static final String column_AttributeID = "attributeID";
        public static final String column_Sequence = "sequence";
        public static final String column_Value = "value";

        public static final String[][] columns = {
                {
                        "_id", "integer"
                },
                {
                        "attributeID", "integer"
                },
                {
                        "sequence", "integer"
                },
                {
                        "value", "text"
                }
        };

        public static String[] allColumns = {
                column_ID,
                column_AttributeID,
                column_Sequence,
                column_Value
        };

        public static final String showColumn = "value";

        public static final String defaultSortOrder = column_Sequence;

        public static String createCommand() {
            String command = "Create table " + table + " (";

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
    }

    public static final class Class {
        public static final Uri Content_Uri = Uri.withAppendedPath(BoxContract.Content_Uri,
                "classes");

        public static final String Content_Type = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/box_classes";
        public static final String Content_Item_Type = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/box_classes";

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

        public static final String defaultSortOrder = column_Description;

        public static String createCommand() {
            String command = "Create table " + table + " (";

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
    }

    public static final class ClassAttribute {
        public static final Uri Content_Uri = Uri.withAppendedPath(BoxContract.Content_Uri,
                "classAttributes");

        public static final String Content_Type = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/box_class_attributes";
        public static final String Content_Item_Type = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/box_class_attributes";

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

        public static final String defaultSortOrder = column_Sequence;

        public static String createCommand() {
            String command = "Create table " + table + " (";

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
    }

    public static final class Entity {
        public static final Uri Content_Uri = Uri.withAppendedPath(BoxContract.Content_Uri,
                "entities");

        public static final String Content_Type = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/box_entities";
        public static final String Content_Item_Type = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/box_entities";

        public static final String table = "litterEntity";

        public static final String column_ID = "_id";
        public static final String column_Description = "description";
        public static final String column_ClassID = "classID";

        public static final String[][] columns = {
                {
                        column_ID, "integer"
                }, {
                        column_Description, "text"
                }, {
                        column_ClassID, "integer"
                }
        };

        public static final String showColumn = "description";

        public static final String defaultSortOrder = column_Description;

        public static String createCommand() {
            String command = "Create table " + table + " (";

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
    }

    public static final class EntityAttribute {
        public static final Uri Content_Uri = Uri.withAppendedPath(BoxContract.Content_Uri,
                "entityAttributes");

        public static final String Content_Type = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/box_entity_attributes";
        public static final String Content_Item_Type = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/box_entity_attributes";

        public static final String table = "litterEntityAttribute";

        public static final String column_ID = "_id";
        public static final String column_EntityID = "entityID";
        public static final String column_AttributeID = "attributeID";
        public static final String column_ValueID = "valueID";
        public static final String column_ValueData = "valueData";
        public static final String[][] columns = {
                {
                        column_ID, "integer"
                },
                {
                        column_EntityID, "integer"
                },
                {
                        column_AttributeID, "integer"
                },
                {
                        column_ValueID, "integer"
                },
                {
                        column_ValueData, "text"
                }
        };

        public static final String showColumn = "valueData";

        public static final String defaultSortOrder = column_AttributeID + ", " + column_ValueData;

        public static String createCommand() {
            String command = "Create table " + table + " (";

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
    }
}
