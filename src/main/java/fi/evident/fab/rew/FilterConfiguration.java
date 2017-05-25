package fi.evident.fab.rew;

import java.util.Collections;
import java.util.List;

public class FilterConfiguration {

    private final FilterSlope slope;
    private final FilterPlacement placement;
    private final List<Filter> filters;

    public FilterConfiguration(FilterSlope slope, FilterPlacement placement, List<Filter> filters) {
        this.slope = slope;
        this.placement = placement;
        this.filters = filters;
    }

    public FilterSlope getSlope() {
        return slope;
    }

    public FilterPlacement getPlacement() {
        return placement;
    }

    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        result.append("Filter configuration (").append(filters.size()).append(")\n");
        result.append("====================\n");
        result.append("Placement: ").append(placement).append("\n");
        result.append("Slope: ").append(slope).append("\n");

        for (Filter filter : filters) {
            result.append("Filter: ").append(filter).append("\n");
        }

        return result.toString();
    }
}
