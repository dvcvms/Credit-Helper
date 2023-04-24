package ru.mts.credit_registration.serivce;

import ru.mts.credit_registration.model.DataResponse;
import ru.mts.credit_registration.model.DataTariffsResponse;

public interface TariffService {

    DataResponse<DataTariffsResponse> getTariffs();

    Boolean existsById(Long tariffId);

}
