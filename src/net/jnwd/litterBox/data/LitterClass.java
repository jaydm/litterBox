package net.jnwd.litterBox.data;

public class LitterClass {
	public static final String table = "litterClass";

	public static final String[][] columns = {
		{
			"_id", "integer"
		},
		{
			"description", "text"
		},
		{
			"container", "integer"
		}
	};

	private Long id;
	private String description;
	private Boolean container;

	public LitterClass() {
		super();
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

	public Boolean getContainer() {
		return container;
	}

	public void setContainer(Boolean container) {
		this.container = container;
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
}
