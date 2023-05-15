package com.codecool.seamanager.model.request;

import java.time.LocalDate;

public record ContractRequest(LocalDate startDate, LocalDate endDate) {
}
