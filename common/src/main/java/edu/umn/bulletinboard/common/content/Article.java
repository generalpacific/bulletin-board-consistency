package edu.umn.bulletinboard.common.content;


import java.util.List;

/**
 * Encapsulates an article.
 * 
 * @author Abhijeet
 *
 */
public class Article {

    /**
     * Unique id of the article. Assumption that number of articles will never be
     * greater than int max.
     */
	private int id;

    /**
     * Article text.
     */
	private String text;

    /**
     * List of replies to this article.
     */
    private List<Integer> replies;
	
	private static final int MIN_TXT_LEN = 10;

    public Article(int id, String text) {
        this.id = id;
        this.text = text;
    }

	public int getId() {
		return id;
	}

    public void setId(int id) {
        this.id = id;
    }

	public String getText() {
		return text;
	}
	
    public List<Integer> getReplies() {
        return replies;
    }

    public void setReplies(List<Integer> replies) {
        this.replies = replies;
    }

    public void addReplies(int replies) {
        this.replies.add(replies);
    }

    /**
     * Check if this article has any replies.
     *
     * @return true if Article has replies
     */
    public boolean hasReplies() {
        if (null == replies) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        if (id != article.id) return false;
        if (!text.equals(article.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new String(id+": "+text);
    }
}
