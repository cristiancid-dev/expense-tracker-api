ALTER TABLE transactions
ADD COLUMN type VARCHAR(20) NOT NULL;

ALTER TABLE transactions
ADD CONSTRAINT chk_transaction_type
CHECK (type IN ('INCOME', 'EXPENSE'));