package com.PicosFrelas.backend.dto;

import com.PicosFrelas.backend.model.Gig;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class GigResponseDto {
    private UUID id;
    private String title;
    private String description;
    private String category;
    private String locationCity;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private String status;
    private OffsetDateTime createdAt;
    private String creatorName;

    public static GigResponseDto fromEntity(Gig gig) {
        GigResponseDto dto = new GigResponseDto();
        dto.setId(gig.getId());
        dto.setTitle(gig.getTitle());
        dto.setDescription(gig.getDescription());
        dto.setCategory(gig.getCategory());
        dto.setLocationCity(gig.getLocationCity());
        dto.setBudgetMin(gig.getBudgetMin());
        dto.setBudgetMax(gig.getBudgetMax());
        dto.setStatus(gig.getStatus().toString());
        dto.setCreatedAt(gig.getCreatedAt());
        dto.setCreatorName(gig.getCreator().getName());
        return dto;
    }
}