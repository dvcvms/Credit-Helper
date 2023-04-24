package ru.mts.credit_registration.serivce.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.model.DataResponse;
import ru.mts.credit_registration.model.DataTariffsResponse;
import ru.mts.credit_registration.repository.TariffRepository;
import ru.mts.credit_registration.serivce.TariffService;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    public DataResponse<DataTariffsResponse> getTariffs() {
        return new DataResponse<>(new DataTariffsResponse(tariffRepository.findAll().orElseThrow()));
    }

    public Boolean existsById(Long tariffId) {
        return tariffRepository.existsById(tariffId).orElseThrow();
    }
}
