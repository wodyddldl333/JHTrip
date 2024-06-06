package com.ssafy.jhtrip.map.dto;

public class TripContent {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String addr1;
    private String firstImage;

    public TripContent() {}

    public TripContent(int contentId, int contentTypeId, String title, String addr1, String firstImage) {
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.title = title;
        this.addr1 = addr1;
        this.firstImage = firstImage;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(int contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    @Override
    public String toString() {
        return "TripContent{" +
                "contentId=" + contentId +
                ", contentTypeId=" + contentTypeId +
                ", title='" + title + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", firstImage='" + firstImage + '\'' +
                '}';
    }
}
