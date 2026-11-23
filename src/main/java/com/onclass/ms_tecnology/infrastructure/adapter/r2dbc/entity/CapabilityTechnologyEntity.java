package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("capability_technologies")
public class CapabilityTechnologyEntity {

    @Id
    private Long id;

    @Column("id_capability")
    private Long capabilityId;

    @Column("id_technology")
    private Long technologyId;
}
