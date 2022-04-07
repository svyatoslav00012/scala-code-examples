package main.bankDbImmutable

import main.bankDbImmutable.IOHelpers.tab

case class Bank(name: String, swiftCode: String, accounts: List[Account]) {
  //    def withdrawFromAccount(number: String, amount: Int): Either[String, Bank] =
  //      withAccount(number)(_.withdraw(amount))
  //
  //    def topUpAccount(number: String, amount: Int): Either[String, Bank] =
  //      withAccount(number)(_.topup(amount))
  //
  //    def withAccount(number: String)(p: Account => Either[String, Account]): Either[String, Bank] =
  //      findAccount(number) match {
  //        case None => Left(s"Account to withdraw from not found. number: $number")
  //        case Some(acc) => p(acc).map(updateAccount)
  //      }

  def updateAccount(updatedAccount: Account): Bank = copy(accounts = accounts.map(a =>
    if (a.number == updatedAccount.number) updatedAccount else a
  ))

  def findAccount(number: String): Option[Account] = accounts.find(_.number == number)

  override def toString: String =
    s"""$name ($swiftCode)
       |${tab(accounts.mkString("\n"))}
       |""".stripMargin
}
