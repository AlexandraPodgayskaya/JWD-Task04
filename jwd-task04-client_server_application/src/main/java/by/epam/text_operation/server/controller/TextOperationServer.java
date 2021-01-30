package by.epam.text_operation.server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import by.epam.text_operation.common.entity.Request;
import by.epam.text_operation.common.entity.Respons;
import by.epam.text_operation.exception.ServerException;
import by.epam.text_operation.server.controller.processing.ProcessingRequest;
import by.epam.text_operation.server.validation.RequestValidator;

public class TextOperationServer {

	public static void main(String[] args) {
		TextOperationServer server = new TextOperationServer();
		try {
			server.go();
		} catch (ServerException e) {
			System.err.println(e.getMessage());
		}
	}

	public void go() throws ServerException {
		int port = 4004;
		try (ServerSocket serverSocket = new ServerSocket(port);
				Socket socket = serverSocket.accept();
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());) {

			Object request = inputStream.readObject();
			Respons respons;
			if (!RequestValidator.correctRequest(request)) {
				respons = new Respons();
				respons.setContent("invalid request");
				outputStream.writeObject(respons);
				throw new ServerException("invalid request");
			}
			Request requestCorrectType = (Request) request;

			ProcessingRequest processingRequest = new ProcessingRequest();
			respons = processingRequest.process(requestCorrectType);
			outputStream.writeObject(respons);

		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
			throw new ServerException(e.getMessage());
		}
	}
}
