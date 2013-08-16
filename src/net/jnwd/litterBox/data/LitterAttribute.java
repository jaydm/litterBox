package net.jnwd.litterBox.data;

import android.content.ContentValues;

public class LitterAttribute {
	public static final String table = "litterAttribute";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"description", "text"
		},
		{
			"type", "text"
		}
	};

	public static final String[] listColumns = {
		"_id",
		"description",
		"type"
	};

	public static final int showColumn = 1; // description

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
