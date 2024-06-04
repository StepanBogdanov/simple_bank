package com.bogstepan.simple_bank.deal.model.json;

import com.bogstepan.simple_bank.deal.model.dto.StatementStatusHistoryDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StatusHistory implements Serializable {

    private List<StatementStatusHistoryDto> statusHistory;
}
