package by.epam.text_operation.server.entity.part_text;

import java.io.Serializable;
import java.util.List;

public interface ComponentText extends Serializable {

	public void show(StringBuilder text);
	public TypeComponent getType();
	public List<String> getContent();

}
