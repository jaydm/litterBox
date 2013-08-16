package net.jnwd.litterBox.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

public class LitterEntity {
	public static final String table = "litterEntity";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"description", "text"
		}, {
			"classID", "integer"
		}
	};

	private Long id;
	private String description;
	private Long classID;

	public LitterEntity() {
		super();
	}

	public LitterEntity(String desc, long classID) {
		this();

		description = desc;
		this.classID = classID;
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

	public Long getClassID() {
		return classID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}

	public static String createCommand() {
		String command = "Create table " + LitterEntity.table + " (";

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
		initialValues.put(columns[2][0], classID);

		return initialValues;
	}

	public boolean updateAttributeValue(int[] path, Long valueID, String valueData) {
		// This routine will need to follow the given path
		// and update the appropriate value...
		// if successful - the return value should be true
		// otherwise - false

		return false;
	}

	public static String[] allColumns() {
		List<String> columnNames = new ArrayList<String>();

		for (int i = 0; i < columns.length; i++) {
			columnNames.add(columns[i][0]);
		}

		return (String[]) columnNames.toArray();
	}
}
