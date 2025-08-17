package com.xshevska.issuetracker.domain.model;

import java.time.format.DateTimeFormatter;

public record Issue(
        String id,
        String description,
        String parentId,
        Status status,
        java.time.OffsetDateTime createdAt,
        java.time.OffsetDateTime updatedAt
) {
    private static final DateTimeFormatter DISP_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public String toString() {
        String c = createdAt == null ? "" : createdAt.withSecond(0).withNano(0)
                .toLocalDateTime().format(DISP_FMT);
        String u = updatedAt == null ? "" : updatedAt.withSecond(0).withNano(0)
                .toLocalDateTime().format(DISP_FMT);

        return """
                Issue %s [%s]
                  Desc: %s
                  Parent: %s
                  Created: %s
                  Updated: %s
                """.formatted(id, status, description, parentId, c, u);
    }
}