# ����������� "�����������"

### *POST: /calculator/offers*

1. �� API �������� `LoanStatementRequestDto`.  
2. �� ��������� `LoanStatementRequestDto`  �������� 4 ��������� ����������� `LoanOfferDto` �� ��������� ���� ��������� ���������� ��������� ����� `isInsuranceEnabled` � `isSalaryClient` (false-false, false-true, true-false, true-true).  
3. ����� �� API - ������ �� 4� `LoanOfferDto` �� "�������" � "�������" (��� ������ �������� ������, ��� �����).

### *POST: /calculator/calc*

1. �� API �������� `ScoringDataDto`.  
2. ���������� ������� ������, ������������ �������� ������(`rate`), ������ ��������� �������(`psk`), ������ ������������ �������(`monthlyPayment`), ������ ����������� �������� (`List<PaymentScheduleElementDto>`).   
3. ����� �� API - `CreditDto`, ���������� ����� ������������� �����������.
