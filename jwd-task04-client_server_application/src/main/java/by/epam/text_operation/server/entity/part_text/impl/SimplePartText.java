package by.epam.text_operation.server.entity.part_text.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.TypeComponent;

public class SimplePartText implements ComponentText {

	private static final long serialVersionUID = 1L;
	private TypeComponent type;
	private String value;

	public SimplePartText() {
	}

	public SimplePartText(TypeComponent type, String content) {
		this.type = type;
		this.value = content;
	}

	public TypeComponent getType() {
		return type;
	}

	public void setType(TypeComponent type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public List <String> getContent() {
		List <String> contentList = new ArrayList <String>();
		contentList.add(value);
		return contentList;
	}
	
	
	@Override
	public void show(StringBuilder text) {
		text.append(this.value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplePartText other = (SimplePartText) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equalsIgnoreCase(other.value))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimplePartText [type=" + type + ", value=" + value + "]";
	}
}
