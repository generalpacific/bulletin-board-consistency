package edu.umn.bulletinboard.common.exception;

/**
 *
 * Copied from Server.
 * INvalidArticleException.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class InvalidArticleException extends Exception {

    private static final long serialVersionUID = -2746486945195270173L;

    public InvalidArticleException(){
        super();
    }

    public InvalidArticleException(String s){
        super(s);
    }


}
