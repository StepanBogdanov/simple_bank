# ����������� "������"

### *POST: /statement*

1. �� API �������� `LoanStatementRequestDto`
2. �� ������ `LoanStatementRequestDto` ���������� ����������.  
3. ������������ POST-������ �� */deal/statement* � �� deal ����� FeignClient.  
4. ����� �� API - ������ �� 4� `LoanOfferDto` �� "�������" � "�������".  

### *POST: /statement/offer*

1. �� API �������� `LoanOfferDto`  
2. ������������ POST-������ �� */deal/offer/select* � �� deal ����� FeignClient.
