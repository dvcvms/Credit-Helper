package ru.mts.credit_registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mts.credit_registration.entity.TariffEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class DataTariffsResponse {
    private List<TariffEntity> tariffs;
}
