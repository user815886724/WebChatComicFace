package com.face.model;

public class WordModel {
    private String WordId;
    private String pictureUrl;
    private String result;

    public String getWordId() {
        return WordId;
    }

    public void setWordId(String wordId) {
        WordId = wordId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
