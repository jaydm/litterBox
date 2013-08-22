package net.jnwd.litterBox.data;

import android.content.ContentValues;

public class LitterAttribute {
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

	public static String[] allColumns = {
		column_ID,
		column_Description,
		column_Type
	};

	public static final String showColumn = column_Description;

	private Long id;
	private String description;
	private String type;

	public LitterAttribute() {
		super();
	}

	public LitterAttribute(String desc, String typ) {
		this();

		description = desc;
		type = typ;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static String createCommand() {
		String command = "Create table " + LitterAttribute.table + " (";

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
		initialValues.put(columns[2][0], type);

		return initialValues;
	}
}
