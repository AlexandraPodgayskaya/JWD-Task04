package by.epam.text_operation.server.entity.part_text.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.TypeComponent;

public class CompositePartText implements ComponentText {

	private static final long serialVersionUID = 1L;
	private TypeComponent type;
	private List<ComponentText> components;

	public CompositePartText() {
	}

	public CompositePartText(TypeComponent type) {
		this.type = type;
	}

	public TypeComponent getType() {
		return type;
	}

	public void setType(TypeComponent type) {
		this.type = type;
	}

	public List<ComponentText> getComponent() {
		return components;
	}

	public void addComponent(ComponentText componentText) {
		if (components == null) {
			components = new ArrayList<ComponentText>();
		}
		components.add(componentText);
	}

	public void addComponent(List<? extends ComponentText> componentsText) {
		if (components == null) {
			components = new ArrayList<ComponentText>();
		}
		components.addAll(componentsText);
	}

	@Override
	public List<String> getContent() {
		List<String> content = new ArrayList<String>();
		for (ComponentText component : components) {
			content.addAll(component.getContent());
		}

		return content;
	}

	@Override
	public void show(StringBuilder text) {
		for (ComponentText component : this.components) {
			component.show(text);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((components == null) ? 0 : components.hashCode());
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
		CompositePartText other = (CompositePartText) obj;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompositePartText [type=" + type + ", components=" + components + "]";
	}

}