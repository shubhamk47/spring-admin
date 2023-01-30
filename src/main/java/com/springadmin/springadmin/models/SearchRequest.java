package com.springadmin.springadmin.models;

import java.util.List;

public class SearchRequest {

    private List<SearchCriteria> searchCriteriaList;
    // Should always be 1 less than searchCriteriaList;
    // Test design = 1
    private String searchCriteraOptions;

    public SearchRequest() {
    }

    public SearchRequest(List<SearchCriteria> searchCriteriaList, String searchCriteraOptions) {
        this.searchCriteriaList = searchCriteriaList;
        this.searchCriteraOptions = searchCriteraOptions;
    }

    public List<SearchCriteria> getSearchCriteriaList() {
        return searchCriteriaList;
    }

    public void setSearchCriteriaList(List<SearchCriteria> searchCriteriaList) {
        this.searchCriteriaList = searchCriteriaList;
    }

    public String getSearchCriteraOptions() {
        return searchCriteraOptions;
    }

    public void setSearchCriteraOptions(String searchCriteraOptions) {
        this.searchCriteraOptions = searchCriteraOptions;
    }
}
