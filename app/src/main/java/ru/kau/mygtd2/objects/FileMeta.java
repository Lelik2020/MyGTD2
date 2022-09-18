package ru.kau.mygtd2.objects;

import androidx.annotation.Nullable;

public class FileMeta {


    private String path;


    private String title;


    private String author;
    private String sequence;
    private String genre;
    private String child;
    private String annotation;
    private Integer sIndex;
    private Integer cusType;
    private String ext;


    private Long size;


    private Long date;
    private String dateTxt;
    private String sizeTxt;


    private String pathTxt;
    private Boolean isStar;
    private Long isStarTime;
    private Boolean isRecent;
    private Long isRecentTime;
    private Float isRecentProgress;
    private Boolean isSearchBook;
    private String lang;
    private String tag;
    private Integer pages;
    private String keyword;
    private Integer year;
    private Integer state;
    private String publisher;
    private String isbn;


    private String parentPath;


    public FileMeta() {
    }

    public FileMeta(String path) {
        this.path = path;
    }


    public FileMeta(String path, String title, String author, String sequence, String genre, String child, String annotation, Integer sIndex, Integer cusType, String ext, Long size, Long date, String dateTxt, String sizeTxt, String pathTxt, Boolean isStar, Long isStarTime, Boolean isRecent, Long isRecentTime, Float isRecentProgress, Boolean isSearchBook, String lang, String tag, Integer pages, String keyword, Integer year, Integer state, String publisher, String isbn, String parentPath) {
        this.path = path;
        this.title = title;
        this.author = author;
        this.sequence = sequence;
        this.genre = genre;
        this.child = child;
        this.annotation = annotation;
        this.sIndex = sIndex;
        this.cusType = cusType;
        this.ext = ext;
        this.size = size;
        this.date = date;
        this.dateTxt = dateTxt;
        this.sizeTxt = sizeTxt;
        this.pathTxt = pathTxt;
        this.isStar = isStar;
        this.isStarTime = isStarTime;
        this.isRecent = isRecent;
        this.isRecentTime = isRecentTime;
        this.isRecentProgress = isRecentProgress;
        this.isSearchBook = isSearchBook;
        this.lang = lang;
        this.tag = tag;
        this.pages = pages;
        this.keyword = keyword;
        this.year = year;
        this.state = state;
        this.publisher = publisher;
        this.isbn = isbn;
        this.parentPath = parentPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Integer getSIndex() {
        return sIndex;
    }

    public void setSIndex(Integer sIndex) {
        this.sIndex = sIndex;
    }

    public Integer getCusType() {
        return cusType;
    }

    public void setCusType(Integer cusType) {
        this.cusType = cusType;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDateTxt() {
        return dateTxt;
    }

    public void setDateTxt(String dateTxt) {
        this.dateTxt = dateTxt;
    }

    public String getSizeTxt() {
        return sizeTxt;
    }

    public void setSizeTxt(String sizeTxt) {
        this.sizeTxt = sizeTxt;
    }

    public String getPathTxt() {
        return pathTxt;
    }

    public void setPathTxt(String pathTxt) {
        this.pathTxt = pathTxt;
    }

    public Boolean getIsStar() {
        return isStar;
    }

    public void setIsStar(Boolean isStar) {
        this.isStar = isStar;
    }

    public Long getIsStarTime() {
        return isStarTime;
    }

    public void setIsStarTime(Long isStarTime) {
        this.isStarTime = isStarTime;
    }

    public Boolean getIsRecent() {
        return isRecent;
    }

    public void setIsRecent(Boolean isRecent) {
        this.isRecent = isRecent;
    }

    public Long getIsRecentTime() {
        return isRecentTime;
    }

    public void setIsRecentTime(Long isRecentTime) {
        this.isRecentTime = isRecentTime;
    }

    public Float getIsRecentProgress() {
        return isRecentProgress;
    }

    public void setIsRecentProgress(Float isRecentProgress) {
        this.isRecentProgress = isRecentProgress;
    }

    public Boolean getIsSearchBook() {
        return isSearchBook;
    }

    public void setIsSearchBook(Boolean isSearchBook) {
        this.isSearchBook = isSearchBook;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj != null && path.equals(((FileMeta) obj).path);
    }

}
