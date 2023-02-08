package com.stackoverflowapi.api.filter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class QuestionFilter implements ApiFilter {
    @Override
    public Map<String, String> getUrlFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.put("order", "desc");
        filters.put("sort", "activity");
        filters.put("site", "stackoverflow");
        return filters;
    }
}
