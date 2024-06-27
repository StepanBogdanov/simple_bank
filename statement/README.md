# Микросервис "Заявка"

### *POST: /statement*

1. По API приходит `LoanStatementRequestDto`
2. На основе `LoanStatementRequestDto` происходит прескоринг.  
3. Отправляется POST-запрос на */deal/statement* в МС deal через FeignClient.  
4. Ответ на API - список из 4х `LoanOfferDto` от "худшего" к "лучшему".  

### *POST: /statement/offer*

1. По API приходит `LoanOfferDto`  
2. Отправляется POST-запрос на */deal/offer/select* в МС deal через FeignClient.
