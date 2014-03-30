package edu.umn.bulletinboard.server.storage;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.server.exceptions.InvalidArticleException;

import java.util.*;

/**
 *
 * Memory storage of articles.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class MemStore {

    Map<Integer, Article> articles;

    private static MemStore memstoreInstance = null;

    private MemStore(Map<Integer, Article> articleMap) {
        this.articles = articleMap;
    }

    public synchronized static MemStore getInstance() {
        if (null == memstoreInstance) {
            return MemStore.getInstance(new TreeMap<Integer, Article>());
        }

        return memstoreInstance;
    }

    public synchronized static MemStore getInstance(Map<Integer, Article> articleMap) {

        if (null == memstoreInstance) {

            if (null == articleMap) {
                memstoreInstance = new MemStore(new TreeMap<Integer, Article>());
            }

            memstoreInstance = new MemStore(articleMap);
        }

        return memstoreInstance;
    }

    public void addArticle(Article article) throws InvalidArticleException {
        if (null == article || article.getId() < 0) {
            throw new InvalidArticleException("Memstore cannot store invalid article");
        }

        articles.put(article.getId(), article);
    }

    /**
     * DFS traversal, to print the articles.
     *
     * @return
     */
    public String getArticlesStr() {

        Map<Integer, Boolean> isVisited = new HashMap<Integer, Boolean>();
        StringBuffer str = new StringBuffer();

        Set<Integer> ids = articles.keySet();
        Deque<StkNode> stk = new ArrayDeque<StkNode>();

        for (int id : ids) {

            if (articles.get(id).isVisited()) {
                continue;
            }

            stk.addFirst(new StkNode(id, 1));
            int indent = 0;
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

        cleanVisited();

        return str.toString();
    }

    private void cleanVisited() {
        for (int id : articles.keySet()) {
            articles.get(id).setVisited(false);
        }
    }

    private String indentArticle(int level, Article art) {

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

    private String indentArticle(int level, String article) {

        StringBuffer buf = new StringBuffer();

        return null;
    }

    public StkNode(int id, int level) {
        this.id = id;
        this.level = level;
    }
}