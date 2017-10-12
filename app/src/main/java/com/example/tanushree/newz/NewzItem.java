package com.example.tanushree.newz;

/**
 * Created by tanushree on 06/01/17.
 */

public class NewzItem
{
    private int mArticleId;

    private String mHeadline = null;
    // the url of the article.
    private String mUrl = null;
    // the content of the article
    private String mArticle = null;

    public int getArticleId() {
        return mArticleId;
    }

    public void setArticleId(int articleId) {
        mArticleId = articleId;
    }

    public String getArticle() {
        return mArticle;
    }

    public void setArticle(String article) {
        mArticle = article;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public void setHeadline(String headline) {
        mHeadline = headline;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}