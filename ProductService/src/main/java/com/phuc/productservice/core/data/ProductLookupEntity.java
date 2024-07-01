package com.phuc.productservice.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productlookup")
public class ProductLookupEntity {

    @Id
    private String productId;

    @Column(unique = true)
    private String title;
}
