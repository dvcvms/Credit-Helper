package ru.mts.credit_registration.serivce;

import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.model.*;

import java.sql.Timestamp;
import java.util.List;

public interface LoanOrderService {

    DataResponse<DataLoanOrderResponse> createOrder(LoanApplicationRequest loanApplicationRequest);

    DataResponse<DataStatusResponse> getStatusByOrderId(String orderId);

    void deleteOrder(DeleteApplicationRequest deleteApplicationRequest);

    List<LoanOrderEntity> getByStatus(String status);

    void setStatusByOrderId(String orderId, String status);

    void setTimeUpdateByOrderId(String orderId, Timestamp timeUpdate);

}
