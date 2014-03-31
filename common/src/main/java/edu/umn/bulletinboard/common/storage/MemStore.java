package edu.umn.bulletinboard.common.storage;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.exception.InvalidArticleException;

import java.util.*;

/**
 *
 * Copied from server.
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

    public Article getArticle(int id) {
        return articles.get(id);
    }

    public Map<Integer, Article> getAllArticles() {
        return articles;
    }

}