package pack1;

import java.io.Serializable;

/**
 * @author Frank
 *
 */
public class Quote implements Serializable{
	private String quote;
	private String author;
	
	/**
	 * @param quote
	 * @param author
	 */
	public Quote(String quote, String author) {
		this.quote = quote;
		this.author = author;
	}
	
	public String toString() {
		return quote + "|" + author;
	}
	
	public boolean equals(Quote other) {
		return this.quote.equals(other.quote) &&
				this.author.equals(other.author);
	}
	
	public boolean equals(Object other) {
		Quote otherQ = (Quote)other;
		return this.quote.equals(otherQ.quote) &&
				this.author.equals(otherQ.author);
	}

	/**
	 * @return the quote
	 */
	public String getQuote() {
		return quote;
	}

	/**
	 * @param quote the quote to set
	 */
	public void setQuote(String quote) {
		this.quote = quote;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}
