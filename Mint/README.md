#### Functional Requirements
1) User connects to financial account
2) Service extracts transactions from the account
   Updates daily (For users active in last 30 days)
   Categorizes transactions
   Allows manual category override by the user
   No automatic re-categorization
   Analyzes monthly spending, by category
   Service recommends a budget
   Allows users to manually set a budget
   Sends notifications when approaching or exceeding budget 
   
#### Non-Functional Requirements
1) High Availability
   Estimations (Assumptions):
   Users: 10 million
   Categories: 10 per user
   Budget items: 10M users * 10 categories per user = 100M
   Sellers: 50K
   Financial Accounts: 10M users * 3 = 30M
   Transactions: 5B/month
   Read Requests: 500M / month
   10:1 write to read ratio
   Write-heavy, users make transactions daily, but few visit the site dail

   Storage:
   Transaction(
        user_id: 8 bytes,
        amount: 5 bytes,
        seller: 32 bytes,
        created_at: 5 bytes
   ): 5 bytes

   Transactions: 5 B/month transactions * 5 bytes = 250GB / month = 3TB / year

   Network Bandwidth:
   Transactions: 2000 transactions/second
   Read Requests: 400 read requests/second
   
#### Design

   Schema
   User(user_id (PK), mobile_number)
   Transaction(user_id, amount, seller, created_at)
   Account(id (PK), created_at, last_update_at, account_url, account_login, account_password_hash, user_id (FK))