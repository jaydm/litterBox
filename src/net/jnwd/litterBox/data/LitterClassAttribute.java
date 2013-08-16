package net.jnwd.litterBox.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

public class LitterClassAttribute {
	public static final String table = "litterClassAttribute";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"parentID", "integer"
		},
		{
			"sequence", "integer"
		},
		{
			"classID", "integer"
		},
		{
			"attributeID", "integer"
		}
	};

	private Long id;
	private Long parentID;
	private Long sequence;
	private Long classID;
	private Long attributeID;

	public LitterClassAttribute() {
		super();
	}

	public LitterClassAttribute(Long parentID, long sequence, Long classID, Long attributeID) {
		this();

		this.parentID = parentID;
		this.sequence = sequence;
		this.classID = classID;
		this.attributeID = attributeID;
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
