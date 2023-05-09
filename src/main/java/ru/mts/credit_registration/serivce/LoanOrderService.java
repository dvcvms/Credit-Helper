package ru.mts.credit_registration.serivce;

import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.enums.LoanOrderStatus;
import ru.mts.credit_registration.model.*;

import java.sql.Timestamp;
import java.util.List;

public interface LoanOrderService {

    DataResponse<DataLoanOrderResponse> createOrder(LoanApplicationRequest request);

    DataResponse<DataStatusResponse> getStatusByOrderId(String orderId);

    void deleteOrder(DeleteApplicationRequest request);

    List<LoanOrderEntity> getByStatus(LoanOrderStatus status);

    void setStatusByOrderId(String orderId, LoanOrderStatus status);

    void setTimeUpdateByOrderId(String orderId, Timestamp timeUpdate);

}
