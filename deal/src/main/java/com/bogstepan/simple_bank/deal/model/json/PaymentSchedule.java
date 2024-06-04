package com.bogstepan.simple_bank.deal.model.json;

import com.bogstepan.simple_bank.deal.model.dto.PaymentScheduleElementDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaymentSchedule implements Serializable {

    private List<PaymentScheduleElementDto> paymentSchedule;
}
