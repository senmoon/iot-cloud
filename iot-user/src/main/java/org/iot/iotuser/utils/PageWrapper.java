package org.iot.iotuser.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.iot.iotcommon.QueryOrder;
import org.iot.iotcommon.model.PageCondition;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageWrapper<T> {
    private PageWrapper<T> pageWrapper = null;
    private PageCondition pageCondition;

    public PageWrapper(PageCondition pageCondition) {
        System.out.println(pageCondition.getOrder());
        this.pageCondition = pageCondition;
    }

    public PageWrapper<T> getInstance() {
        if (pageWrapper == null) {
            pageWrapper = new PageWrapper<>(pageCondition);
        }
        return pageWrapper;
    }

    public Page<T> getPage() {
        Page<T> page = new Page<>();
        long current = pageCondition.getCurrent();
        long maxValue = Long.MAX_VALUE;
        if (current > 0 && current < maxValue) {
            page.setCurrent(current);
        }
        long size = pageCondition.getSize();
        if (size > 0 && size < maxValue) {
            page.setSize(size);
        }
        long total = pageCondition.getTotal();
        if (total > 0 && total < maxValue) {
            page.setTotal(total);
        }
        if (pageCondition.isSearchCount()) {
            page.setSearchCount(pageCondition.isSearchCount());
        }
        return page;
    }

    public QueryWrapper<T> getQueryWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        // 模糊匹配多列
        String keyword = pageCondition.getKeyword();
        List<String> columns = pageCondition.getColumns();
        if (!(keyword == null || "".equals(keyword))) {
            if (columns != null) {
                for (int i = 0; i < columns.size(); i++) {
                    String column = columns.get(i);
                    if (i == 0) {
                        wrapper.like(column, keyword);
                    } else {
                        wrapper.or().like(column, keyword);
                    }
                }
            } else {
                System.out.println("需要指定模糊搜索的列");
                return null;
            }
        }
        // 排序
        QueryOrder order = pageCondition.getOrder();
        String field = pageCondition.getField();
        if (order != null) {
            field = camel2underscore(field);
            if (order == QueryOrder.ascend) {
                wrapper.orderByAsc(field);
            } else if (order == QueryOrder.descend) {
                wrapper.orderByDesc(field);
            }
        }
        return wrapper;
    }

    private String camel2underscore(String field) {
        String regexStr = "[A-Z]";
        Matcher matcher = Pattern.compile(regexStr).matcher(field);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String g = matcher.group();
            matcher.appendReplacement(sb, "_" + g.toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.charAt(0) == '_') {
            sb.delete(0, 1);
        }
        return sb.toString();
    }
}
