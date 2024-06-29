# Микросервис "Калькулятор"

### *POST: /calculator/offers*

1. По API приходит `LoanStatementRequestDto`.  
2. На основании `LoanStatementRequestDto`  создаётся 4 кредитных предложения `LoanOfferDto` на основании всех возможных комбинаций булевских полей `isInsuranceEnabled` и `isSalaryClient` (false-false, false-true, true-false, true-true).  
3. Ответ на API - список из 4х `LoanOfferDto` от "худшего" к "лучшему" (чем меньше итоговая ставка, тем лучше).

### *POST: /calculator/calc*

1. По API приходит `ScoringDataDto`.  
2. Происходит скоринг данных, высчитывание итоговой ставки(`rate`), полной стоимости кредита(`psk`), размер ежемесячного платежа(`monthlyPayment`), график ежемесячных платежей (`List<PaymentScheduleElementDto>`).   
3. Ответ на API - `CreditDto`, насыщенный всеми рассчитанными параметрами.
