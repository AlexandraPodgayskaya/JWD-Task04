package by.epam.text_operation.client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import by.epam.text_operation.common.entity.Request;
import by.epam.text_operation.common.entity.Respons;
import by.epam.text_operation.exception.ClientException;
import by.epam.text_operation.server.service.TextOperation;

public class TextOperationClient {
	private final static String HOST = "127.0.0.1";
	private final static String PATH = "resources/Text.txt";

	public static void main(String[] args) {
		TextOperationClient client = new TextOperationClient();

		try {
			client.go();
		} catch (ClientException e) {
			System.err.println(e.getMessage());
		}
	}

	public void go() throws ClientException {
		int port = 4004;
		try (Socket socket = new Socket(HOST, port);
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());) {

			outputStream.writeObject(createRequest());
			Respons respons = (Respons) inputStream.readObject();
			System.out.println(respons.getContent());

		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
			throw new ClientException(e.getMessage());
		}
	}

	public Request createRequest() throws ClientException {
		Request request = new Request();

		request.setFile(new File(PATH));
		request.setOperation(TextOperation.TWO);

		return request;
	}

}