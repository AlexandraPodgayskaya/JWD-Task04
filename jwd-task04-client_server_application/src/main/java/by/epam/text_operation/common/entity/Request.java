package by.epam.text_operation.common.entity;

import java.io.File;
import java.io.Serializable;

import by.epam.text_operation.exception.ClientException;
import by.epam.text_operation.server.service.TextOperation;

public class Request  implements Serializable{

	private static final long serialVersionUID = 1L;

	private TextOperation operation;
	private File file;
	private String otherInformation;

	public Request() {
	}

	public TextOperation getOperation() {
		return operation;
	}

	public void setOperation(TextOperation numberOperation) {
		this.operation = numberOperation;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) throws ClientException {
		if (file == null) {
			throw new ClientException ("file not found");
		}
		this.file = file;
	}

	public String getOtherInformation() {
		return otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((otherInformation == null) ? 0 : otherInformation.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		Request other = (Request) obj;
		if (operation != other.operation)
			return false;
		if (otherInformation == null) {
			if (other.otherInformation != null)
				return false;
		} else if (!otherInformation.equals(other.otherInformation))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [operation=" + operation + ", file=" + file + ", otherInformation="
				+ otherInformation + "]";
	}

}