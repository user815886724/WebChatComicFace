package com.face.entity;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageInfo<T> {

    public PageInfo() {
    }

    public PageInfo(long total, int number, int size, Boolean first, Boolean last, int numberOfElements, int totalPages) {
        this.total = total;
        this.number = number;
        this.size = size;
        this.first = first;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
    }

    public PageInfo(Page page){
        this.size = page.getSize();
        this.number = page.getNumber();
        this.total = page.getTotalElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.numberOfElements = page.getNumberOfElements();
        this.totalPages = page.getTotalPages();
        this.datas = page.getContent();
    }

    private long total;

    private int number;

    private int size;

    private Boolean first;

    private Boolean last;

    private int numberOfElements;

    private int totalPages;

    private List<T> datas;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
