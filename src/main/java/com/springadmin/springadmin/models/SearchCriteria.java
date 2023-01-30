package com.springadmin.springadmin.models;

public class SearchCriteria {

    private String filterClass;
    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteria() {
    }

    public SearchCriteria(String filterClass, String filterKey, Object value, String operation, String dataOption) {
        this.filterClass = filterClass;
        this.filterKey = filterKey;
        this.value = value;
        this.operation = operation;
        this.dataOption = dataOption;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDataOption() {
        return dataOption;
    }

    public void setDataOption(String dataOption) {
        this.dataOption = dataOption;
    }
}
