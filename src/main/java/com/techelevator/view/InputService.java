package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class InputService {
	private PrintWriter out;
	private Scanner in;

	public InputService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public String getUserInput(String prompt) {
		out.print(prompt);
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt);
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
				if (result < 0) {
					System.out.println("\n>>> No negative wagers, cheater! Try again. <<<\n");
					result = null;
				}
			} catch(NumberFormatException e) {
				out.println("\n>>> Invalid response <<<\n");
			}
		} while(result == null);
		return result;
	}

}
