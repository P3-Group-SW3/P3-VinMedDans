package com.vmd.vmdwebshop.model;

import com.vmd.vmdwebshop.service.UserService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Customer_tbl")
public class Customer extends User{

    @Column(name = "legal_age")
    private boolean legalAge;

    public Customer(String id, boolean legalAge) {
        super(id);
        this.legalAge = legalAge;
    }

    
}
