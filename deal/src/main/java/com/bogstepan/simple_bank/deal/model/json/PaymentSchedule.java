package com.bogstepan.simple_bank.deal.model.json;

import com.bogstepan.simple_bank.calculator_client.dto.PaymentScheduleElementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSchedule implements Serializable {

    private List<PaymentScheduleElementDto> paymentSchedule;
}
