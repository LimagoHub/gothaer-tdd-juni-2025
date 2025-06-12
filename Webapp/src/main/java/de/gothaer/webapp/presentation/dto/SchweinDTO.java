package de.gothaer.webapp.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchweinDTO {

    @NotNull
    private UUID id;

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @DecimalMin("10")
    private int gewicht;


}
