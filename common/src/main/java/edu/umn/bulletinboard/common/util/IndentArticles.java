package edu.umn.bulletinboard.common.util;

import edu.umn.bulletinboard.common.content.Article;

import java.util.*;

/**
 *
 * Indent a list of articles.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class IndentArticles {
	
	private IndentArticles() {
		throw new IllegalStateException("Cannot instantiate this class.");
	}
	
	public static Map<Integer, Article> getArticleMap(List<Article> articles) {
		Map<Integer,Article> map = new TreeMap<Integer, Article>();
		
		for (Article article : articles) {
			map.put(article.getId(), article);
		}
		return map;
	}

    /**
     * DFS traversal, to print the articles.
     *
     * @return
     */
    public static String getArticlesStr(Map<Integer, Article> articles) {

        StringBuffer str = new StringBuffer();

        Set<Integer> ids = articles.keySet();
        Deque<StkNode> stk = new ArrayDeque<StkNode>();

        for (int id : ids) {

            if (articles.get(id).isVisited()) {
                continue;
            }

            stk.addFirst(new StkNode(id, 1));
            while (!stk.isEmpty()) {
                StkNode top = stk.removeFirst();

                List<Integer> children = new ArrayList<Integer>(articles.get(top.getId()).getReplies());
                Collections.reverse(children);

                for (int id1 : children) {
                    stk.addFirst(new StkNode(id1, top.getLevel() + 1));
                }

                str.append(indentArticle(top.getLevel(), articles.get(top.getId())));
                articles.get(top.getId()).setVisited(true);
            }
        }

        cleanVisited(articles);

        return str.toString();
    }

    private static void cleanVisited(Map<Integer, Article> articles) {
        for (int id : articles.keySet()) {
            articles.get(id).setVisited(false);
        }
    }

    private static String indentArticle(int level, Article art) {

        StringBuffer buf = new StringBuffer("\n");

        for (int i = 0; i < level; ++i) {
            buf.append("\t");
        }

        return buf.append(art).toString();
    }

}

class StkNode {

    private int id;

    public int getLevel() {
        return level;
    }

    public int getId() {
        return id;
    }

    private int level;

    public StkNode(int id, int level) {
        this.id = id;
        this.level = level;
    }
}
