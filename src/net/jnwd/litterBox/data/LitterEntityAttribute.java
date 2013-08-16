package net.jnwd.litterBox.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

public class LitterEntityAttribute {
	public static final String table = "litterEntityAttribute";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"entityID", "integer"
		},
		{
			"attributeID", "integer"
		},
		{
			"valueID", "integer"
		},
		{
			"valueData", "text"
		}
	};

	private Long id;
	private Long entityID;
	private Long attributeID;
	private Long valueID;
	private String valueData;

	public LitterEntityAttribute() {
		super();
	}

	public LitterEntityAttribute(Long entityID, Long attributeID, Long valueID, String valueData) {
		this();

		this.entityID = entityID;
		this.attributeID = attributeID;
		this.valueID = valueID;
		this.valueData = valueData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityID() {
		return entityID;
	}

	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	public Long getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(Long attributeID) {
		this.attributeID = attributeID;
	}

	public Long getValueID() {
		return valueID;
	}

	public void setValueID(Long valueID) {
		this.valueID = valueID;
	}

	public String getValueData() {
		return valueData;
	}

	public void setValueData(String valueData) {
		this.valueData = valueData;
	}

	public static String createCommand() {
		String command = "Create table " + LitterEntityAttribute.table + " (";

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

		initialValues.put(columns[1][0], entityID);
		initialValues.put(columns[2][0], attributeID);
		initialValues.put(columns[3][0], valueID);
		initialValues.put(columns[4][0], valueData);

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
