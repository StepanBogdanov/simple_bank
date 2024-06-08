# Микросервис "Сделка"

### *POST: /deal/statement*

1. По API приходит `LoanStatementRequestDto`
2. На основе `LoanStatementRequestDto` создаётся сущность `Client` и сохраняется в БД.
3. Создаётся `Statement` со связью на только что созданный `Client` и сохраняется в БД.
4. Отправляется POST запрос на */calculator/offers* МС Калькулятор через FeignClient. 
Каждому элементу из списка `List<LoanOfferDto>` присваивается id созданной заявки (`Statement`)
5. Ответ на API - список из 4х `LoanOfferDto` от "худшего" к "лучшему".

### *POST: /deal/offer/select*

1. По API приходит `LoanOfferDto`
2. Достаётся из БД заявка(`Statement`) по `statementId` из `LoanOfferDto`.
3. В заявке обновляется статус, история статусов(`List<StatementStatusHistoryDto>`), принятое предложение `LoanOfferDto` устанавливается в поле `appliedOffer`.
4. Заявка сохраняется.

### *POST: /deal/calculate/{statementId}*

1. По API приходит объект `FinishRegistrationRequestDto` и параметр `statementId` (String).
2. Достаётся из БД заявка(`Statement`) по `statementId`.
3. `ScoringDataDto` насыщается информацией из `FinishRegistrationRequestDto` и `Client`, который хранится в Statement
4. Отправляется POST запрос на */calculator/calc* МС Калькулятор с телом `ScoringDataDto` через FeignClient.
5. На основе полученного из кредитного конвейера `CreditDto` создаётся сущность `Credit` и сохраняется в базу со статусом CALCULATED.
6. В заявке обновляется статус, история статусов.
7. Заявка сохраняется.