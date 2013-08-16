package net.jnwd.litterBox.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

public class LitterClass {
	public static final String table = "litterClass";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"description", "text"
		}
	};

	private Long id;
	private String description;

	public LitterClass() {
		super();
	}

	public LitterClass(String desc) {
		this();

		description = desc;
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

	public static String[] allColumns() {
		List<String> columnNames = new ArrayList<String>();

		for (int i = 0; i < columns.length; i++) {
			columnNames.add(columns[i][0]);
		}

		return (String[]) columnNames.toArray();
	}
}
