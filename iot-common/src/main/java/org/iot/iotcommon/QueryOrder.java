package org.iot.iotcommon;

public enum QueryOrder {
    ascend("ascend", "升序"),
    descend("descend", "降序");

    private String order;
    private String text;

    QueryOrder(String order, String text) {
        this.order = order;
        this.text = text;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "QueryOrder{" +
                "order='" + order + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
