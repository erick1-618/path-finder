package br.com.erick.pf.exceptions;

public class PathNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PathNotFoundException() {
		super("A path was not found with this setup");
	}
}
