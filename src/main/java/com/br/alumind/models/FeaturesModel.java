
package com.br.alumind.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@ToString(exclude = "")
@Entity
@Table(name="features", schema = "public")
public class FeaturesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(unique = true)
    String code;
    @Column(length = 102400)
    String reason;
    UUID feedbackId;

}
