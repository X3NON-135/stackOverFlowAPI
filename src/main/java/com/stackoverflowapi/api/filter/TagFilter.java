package com.stackoverflowapi.api.filter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TagFilter implements ApiFilter {
    @Override
    public Map<String, String> getUrlFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.put("site", "stackoverflow");
        return filters;
    }
}
