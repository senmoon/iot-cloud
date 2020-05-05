package org.iot.iotcommon.model;

import org.iot.iotcommon.QueryOrder;

import java.io.Serializable;
import java.util.List;

public class PageCondition implements Serializable {
    private long current;
    private long size;
    private long total;
    private boolean isSearchCount;
    // -- required condition start --
    private String keyword;
    private List<String> columns; // the columns of fuzzy searched // -- required condition end --

    // 升序或降序 -- start --
    private QueryOrder order;
    private String field;
    // 升序或降序 -- end --

    public PageCondition() {
    }

    public PageCondition(long current, long size, long total, boolean isSearchCount, String keyword, List<String> columns, QueryOrder order, String field) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
        this.keyword = keyword;
        this.columns = columns;
        this.order = order;
        this.field = field;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isSearchCount() {
        return isSearchCount;
    }

    public void setSearchCount(boolean searchCount) {
        isSearchCount = searchCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public QueryOrder getOrder() {
        return order;
    }

    public void setOrder(QueryOrder order) {
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "PageCondition{" +
                "current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", isSearchCount=" + isSearchCount +
                ", keyword='" + keyword + '\'' +
                ", columns=" + columns +
                ", order=" + order +
                ", field='" + field + '\'' +
                '}';
    }
}
