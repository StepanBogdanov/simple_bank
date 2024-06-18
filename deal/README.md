# ����������� "������"

### *POST: /deal/statement*

1. �� API �������� `LoanStatementRequestDto`
2. �� ������ `LoanStatementRequestDto` �������� �������� `Client` � ����������� � ��.
3. �������� `Statement` �� ������ �� ������ ��� ��������� `Client` � ����������� � ��.
4. ������������ POST ������ �� */calculator/offers* �� ����������� ����� FeignClient. 
������� �������� �� ������ `List<LoanOfferDto>` ������������� id ��������� ������ (`Statement`)
5. ����� �� API - ������ �� 4� `LoanOfferDto` �� "�������" � "�������".

### *POST: /deal/offer/select*

1. �� API �������� `LoanOfferDto`
2. �������� �� �� ������(`Statement`) �� `statementId` �� `LoanOfferDto`.
3. � ������ ����������� ������, ������� ��������(`List<StatementStatusHistoryDto>`), �������� ����������� `LoanOfferDto` ��������������� � ���� `appliedOffer`.
4. ������ �����������.

### *POST: /deal/calculate/{statementId}*

1. �� API �������� ������ `FinishRegistrationRequestDto` � �������� `statementId` (String).
2. �������� �� �� ������(`Statement`) �� `statementId`.
3. `ScoringDataDto` ���������� ����������� �� `FinishRegistrationRequestDto` � `Client`, ������� �������� � Statement
4. ������������ POST ������ �� */calculator/calc* �� ����������� � ����� `ScoringDataDto` ����� FeignClient.
5. �� ������ ����������� �� ���������� ��������� `CreditDto` �������� �������� `Credit` � ����������� � ���� �� �������� CALCULATED.
6. � ������ ����������� ������, ������� ��������.
7. ������ �����������.