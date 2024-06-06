package com.ssafy.jhtrip.plan;

public class SearchCondition {

    // 한 페이지에 몇 개를 보여줄 것인지 결정
    public final int countPerPage = 8;

    // 검색 컬럼명
    private String key = "none";  // none은 컬럼이 없는 경우

    // 검색어
    private String word;

    // 검색 결과를 정렬할 컬럼명
    private String orderBy = "none";

    // 정렬 방향
    private String orderByDir = "asc";

    // 현재 페이지 번호 (offset을 계산)
    private int currentPage = 1;

    // limit을 적용할 것인지 설정
    private boolean limit = true;

    public SearchCondition() {}

    public SearchCondition(String key, String word, String orderBy, String orderByDir, int currentPage, boolean limit) {
        this.key = key;
        this.word = word;
        this.orderBy = orderBy;
        this.orderByDir = orderByDir;
        this.currentPage = currentPage;
        this.limit = limit;
    }

    public SearchCondition(String key, String word, String orderBy, String orderByDir, int currentPage) {
        this(key, word, orderBy, orderByDir, currentPage, true);
    }

    public SearchCondition(String key, String word, String orderBy, String orderByDir) {
        this(key, word, orderBy, orderByDir, 1);
    }

    public SearchCondition(String key, String word, String orderBy) {
        this(key, word, orderBy, "asc");
    }

    public SearchCondition(String key, String word) {
        this(key, word, "none");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByDir() {
        return orderByDir;
    }

    public void setOrderByDir(String orderByDir) {
        this.orderByDir = orderByDir;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public int getOffset() {
        return (this.currentPage - 1) * countPerPage;
    }

    @Override
    public String toString() {
        return "SearchCondition [countPerPage=" + countPerPage + ", key=" + key + ", word=" + word + ", orderBy="
                + orderBy + ", orderByDir=" + orderByDir + ", currentPage=" + currentPage + ", limit=" + limit + "]";
    }

}