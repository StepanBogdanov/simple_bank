Микросервис "Калькулятор"

POST: /calculator/offers

По API приходит LoanStatementRequestDto.
На основании LoanStatementRequestDto происходит прескоринг, создаётся 4 кредитных предложения LoanOfferDto на основании всех возможных комбинаций булевских полей isInsuranceEnabled и isSalaryClient (false-false, false-true, true-false, true-true).
Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему" (чем меньше итоговая ставка, тем лучше).

POST: /calculator/calc

По API приходит ScoringDataDto.
Происходит скоринг данных, высчитывание итоговой ставки(rate), полной стоимости кредита(psk), размер ежемесячного платежа(monthlyPayment), график ежемесячных платежей (List<PaymentScheduleElementDto>). 
Ответ на API - CreditDto, насыщенный всеми рассчитанными параметрами.
