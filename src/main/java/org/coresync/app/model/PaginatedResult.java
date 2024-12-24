package org.coresync.app.model;

import java.util.List;

public class PaginatedResult<T> {
        private List<T> data;
        private long totalItems;

        public PaginatedResult(List<T> data, long totalItems) {
            this.data = data;
            this.totalItems = totalItems;
        }

        public List<T> getData() {
            return data;
        }

        public long getTotalItems() {
            return totalItems;
        }
}
