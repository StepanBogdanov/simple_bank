����������� "�����������"

POST: /calculator/offers

�� API �������� LoanStatementRequestDto.
�� ��������� LoanStatementRequestDto ���������� ����������, �������� 4 ��������� ����������� LoanOfferDto �� ��������� ���� ��������� ���������� ��������� ����� isInsuranceEnabled � isSalaryClient (false-false, false-true, true-false, true-true).
����� �� API - ������ �� 4� LoanOfferDto �� "�������" � "�������" (��� ������ �������� ������, ��� �����).

POST: /calculator/calc

�� API �������� ScoringDataDto.
���������� ������� ������, ������������ �������� ������(rate), ������ ��������� �������(psk), ������ ������������ �������(monthlyPayment), ������ ����������� �������� (List<PaymentScheduleElementDto>). 
����� �� API - CreditDto, ���������� ����� ������������� �����������.
