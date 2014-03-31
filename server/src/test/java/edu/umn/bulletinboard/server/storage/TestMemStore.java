package edu.umn.bulletinboard.server.storage;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.util.IndentArticles;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * Tests MemStore traversal.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class TestMemStore {

    Map<Integer, Article> articles = new TreeMap<Integer, Article>();

    @Before
    public void setUp() {

        /**
         * Example:
         * 1
         *     2
         *         5
         *     3
         * 4
         *     6
         *     7
         * 5
         */

        Article tmp = createArticle(1);
        tmp.addReplies(2);
        tmp.addReplies(3);
        addArticle(tmp);

        tmp = createArticle(2);
        tmp.addReplies(5);
        addArticle(tmp);

        createAddArticle(3);

        tmp = createArticle(4);
        tmp.addReplies(6);
        tmp.addReplies(7);
        addArticle(tmp);

        createAddArticle(5);
        createAddArticle(6);
        createAddArticle(7);

        MemStore.getInstance(articles);
    }

    private void addArticle(Article art) {
        articles.put(art.getId(), art);
    }

    private Article createArticle(int id) {
        return new Article(id, "Article"+id);
    }

    private void createAddArticle(int id) {
        addArticle(createArticle(id));
    }

    @Test
    public void testTraversal() {
        assertEquals("Mem Store traversal failed.", IndentArticles.getArticlesStr(
                MemStore.getInstance().getAllArticles()), (expectedStr()));

        //to make sure, no internal data structures have changed / worked w/o side effects
        assertEquals("Mem Store traversal changed internal data " +
                "structures.", IndentArticles.getArticlesStr(MemStore.getInstance()
                .getAllArticles()), (expectedStr()));
    }

    private String expectedStr() {
        return "\n" +
                "\t1: Article1\n" +
                "\t\t2: Article2\n" +
                "\t\t\t5: Article5\n" +
                "\t\t3: Article3\n" +
                "\t4: Article4\n" +
                "\t\t6: Article6\n" +
                "\t\t7: Article7";
    }
}
