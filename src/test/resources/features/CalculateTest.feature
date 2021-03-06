#language: ru

Функционал: Проверка лимитов по операциям

  @scenario1
  Сценарий: Снятие наличных с карты, не превышающих по сумме лимита
    * пользователь снимает с "карты" 499 рублей, лимит 500 рублей

  @scenario2
  Сценарий: Снятие наличных с карты, равных по сумме лимиту
    * пользователь снимает с "карты" 500 рублей, лимит 500 рублей

  @scenario3
  Сценарий: Снятие наличных с карты, превышающих по сумме лимит
    * пользователь снимает с "карты" 501 рублей, лимит 500 рублей

  @scenario4
  Сценарий: Снятие денег с аккаунта, не превышающих по сумме лимита
    * пользователь снимает с "аккаунта" 399 рублей, лимит 400 рублей

  @scenario5
  Сценарий: Снятие денег с аккаунта, равных по сумме лимиту
    * пользователь снимает с "аккаунта" 400 рублей, лимит 400 рублей

  @scenario6
  Сценарий: Снятие денег с аккаунта, превышающих по сумме лимит
    * пользователь снимает с "аккаунта" 401 рублей, лимит 400 рублей

  @scenario7
  Сценарий: Снятие денег с карты и аккаунта, превышающих по сумме лимит
    * пользователь снимает с "карты и аккаунта" 400 рублей, лимит 1000 рублей

  @scenario8
  Сценарий: Снятие денег с аккаунта, не превышающих по количеству лимита
    * пользователь проводит 9 операций через аккаунт, лимит 10 операций

  @scenario9
  Сценарий: Снятие денег с аккаунта, превышающих по количеству лимита
    * пользователь проводит 10 операций через аккаунт, лимит 10 операций
