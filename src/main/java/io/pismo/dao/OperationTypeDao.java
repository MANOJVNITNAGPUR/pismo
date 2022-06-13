package io.pismo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation_types")
public class OperationTypeDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "operation_type_id")
    private int operationTypeId;

    private String description;

    @Column(name = "operation_value")
    private int operationValue;
}
