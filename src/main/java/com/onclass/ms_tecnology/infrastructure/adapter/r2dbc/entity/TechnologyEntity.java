package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("technologies")
public class TechnologyEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
