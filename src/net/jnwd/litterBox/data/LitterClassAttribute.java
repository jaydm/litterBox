package net.jnwd.litterBox.data;

public class LitterClassAttribute {
	public static final String table = "litterAttribute";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"classID", "integer"
		},
		{
			"attributeID", "integer"
		}
	};

	private Long id;
	private Long classID;
	private Long attributeID;

	public LitterClassAttribute() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
