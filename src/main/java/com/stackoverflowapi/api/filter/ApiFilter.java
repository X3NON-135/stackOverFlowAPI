package com.stackoverflowapi.api.filter;

import java.util.Map;

public interface ApiFilter {
    Map<String, String> getUrlFilters();
}
