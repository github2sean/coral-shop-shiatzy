package com.dookay.coral.common.persistence.pager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页模型处理
 *
 * @author : kezhan
 * @version : v0.0.1
 * @since : 2016年11月9日
 */
public class PageList<T> implements Serializable {
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    private int pageSize = 10; //分页大小
    private int pageIndex = 1;  // 当前页索引

    private int firstPage = 1;    // 首页索引
    private int lastPage;    // 尾页索引
    private int prevPage;    // 上一页索引
    private int nextPage;    // 下一页索引

    private long startRowIndex;  // 当前页开始行索引
    private long endRowIndex;   // 当前页结束行索引

    private long totalRecord = 0L; // 总记录数
    private int totalPage = 0;   // 总页数

    private boolean isFirstPage; //是否首页
    private boolean isLastPage;     //是否尾页
    private boolean hasPrePage; //是否有上一页
    private boolean hasNextPage;    //是否有下一页
    private List<Integer> pageArray;    // 分页数组，用与分页视图，形如1045609

    private List<T> list;// 本页数据对象列表

    public PageList(List<T> list, int pageIndex, int pageSize, long totalRecord) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.list = list;
        this.calculate();
    }

    private void calculate() {
        startRowIndex = (long) ((pageIndex - 1) * pageSize + 1);
        endRowIndex = (long) (pageIndex * pageSize + 1);
        totalPage = (int) ((totalRecord % pageSize == 0) ? totalRecord / pageSize : totalRecord / pageSize + 1);
        pageIndex = pageIndex >= totalPage ? totalPage : pageIndex;
        lastPage = totalPage;
        prevPage = (int) (pageIndex <= 1L ? 1L : pageIndex - 1);
        nextPage = pageIndex >= totalPage ? totalPage : pageIndex + 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPrevPage() {
        if (isFirstPage()) {
            return pageIndex;
        } else {
            return pageIndex - 1;
        }
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        if (isLastPage()) {
            return pageIndex;
        } else {
            return pageIndex + 1;
        }
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public long getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(long startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public long getEndRowIndex() {
        return endRowIndex;
    }

    public void setEndRowIndex(long endRowIndex) {
        this.endRowIndex = endRowIndex;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isFirstPage() {
        return pageIndex == 1;
    }

    public boolean isLastPage() {
        return pageIndex == totalPage;
    }

    public boolean getHasPrePage() {
        return pageIndex > 1 && pageIndex <= totalPage;
    }

    public boolean isHasNextPage() {
        return pageIndex >= 1 && pageIndex < totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<Integer> getPageArray() {
        return buildPageArray(totalPage, pageIndex);
    }

    /**
     * 构造分页数组
     * 形成如同 1045609 的数组，用于形成 1...456...9式的分页视图
     *
     * @param total
     * @param current
     * @return
     */
    private List<Integer> buildPageArray(Integer total, Integer current) {
        ArrayList<Integer> result = new ArrayList<>();
        if (total < 6) {
            for (int i = 1; i <= total; i++)
                result.add(i);
        } else {
            int start = 1;
            int end = total;
            if (current - 2 > 1) {
                result.add(1);
                result.add(0);
                start = current - 2;
                if (current + 2 < total) {
                    end = current + 2;
                } else {
                    start = total - 4;
                }
            } else {
                end = 5;
            }
            for (int i = start; i <= end; i++)
                result.add(i);
            if (total > end) {
                result.add(0);
                result.add(total);
            }
        }
        return result;
    }
}
