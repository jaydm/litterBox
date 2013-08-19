package net.jnwd.litterBox.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

public class LitterAttributeValue {
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

	private Long id;
	private Long attributeID;
	private Long sequence;
	private String value;

	public LitterAttributeValue() {
		super();
	}

	public LitterAttributeValue(Long attributeID, long sequence, String value) {
		this.attributeID = attributeID;
		this.sequence = sequence;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(Long attributeID) {
		this.attributeID = attributeID;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String createCommand() {
		String command = "Create table " + LitterAttributeValue.table + " (";

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

		initialValues.put(columns[1][0], attributeID);
		initialValues.put(columns[2][0], sequence);
		initialValues.put(columns[3][0], value);

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
