package edu.umn.bulletinboard.common.rmi;

/**
 * Encapsulates an article. It is assumed that the article will be 
 * 
 * @author Abhijeet
 *
 */
public class Article {

	private int id;
	private String text;
	
	private static final int MIN_TXT_LEN = 10;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	/**
	 * Set text as is. Useful when client calls 'choose'!
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Used with ReadAll. Only first few characters of length.
	 * This is useful as 'read' call from client does not have to transfer
	 * whole text over the wire for all the articles.
	 * 
	 * @param text
	 */
	public void setMinText(String text) {
		this.text = text.substring(0, MIN_TXT_LEN);
	}
	
	@Override
	public String toString() {
		return new String(id+": "+text);
	}
	
}
