package main.lecture3.bankDbImmutable

/**
 * test cases:
 * - test each function
 * - test successful withdrawal
 * - test failed withdrawal (not enough money, not existing from account, not existing to account)
 *
 * - test that amount on first account changed
 * - test that amount on second account
 * - test transfers array changed
 * - test failedTransfers array
 */
//Without tests: 30 (without modifiers)
//With tests: 50 (without modifiers)
object ImmutableState {

  def main(args: Array[String]): Unit = {

    val db = DB(
      banks = List(
        Bank("PrivatBank", "123", List(
          Account("Svyatoslav", "1234 5678 9012 3456", 600),
          Account("Mykola", "1234 5678 9012 3457", 300),
        )),
        Bank("MonoBank", "456", List(
          Account("Ivan", "1234 5678 9012 3458", 700),
          Account("Taras", "1234 5678 9012 3459", 900),
        ))
      ),
      transfers = Nil,
      failedTransfers = Nil
    )

    println(db)
    IOHelpers.processInput(db)
  }

  def manualTest(db: DB): Unit = {
    val newDb = db.tryMakeTransfer("1234 5678 9012 3456", "1234 5678 9012 3459", 200)
      .tryMakeTransfer("1234 5678 9012 3459", "1234 5678 9012 3458", 1000)
      .tryMakeTransfer("1234 5678 9012 3456", "1234 5678 9012 3456", 100) // same
      .tryMakeTransfer("1234 5678 9012 3456", "1234 5678 9012 3459", 1000) // not enough money
      .tryMakeTransfer("0000 5678 9012 3456", "1234 5678 9012 3459", 100) // not exists from
      .tryMakeTransfer("1234 5678 9012 3456", "0000 5678 9012 3459", 100) // not exists to
    println(newDb)
  }

}
