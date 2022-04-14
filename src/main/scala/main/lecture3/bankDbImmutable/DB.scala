package main.lecture3.bankDbImmutable

import IOHelpers.tab

case class DB(banks: List[Bank], transfers: List[Transfer], failedTransfers: List[(Transfer, String)]) {
  def tryMakeTransfer(from: String, to: String, amount: Int): DB = {
    (for {
      _ <- if (from == to) Left("Cannot transfer to same account") else Right()
      fromAcc <- findAccount(from)
      toAcc <- findAccount(to)
      updatedWithdrawAcc <- fromAcc.withdraw(amount)
      updatedTopupAcc <- toAcc.topup(amount)
    } yield copy(
      banks = banks.map(_.updateAccount(updatedTopupAcc).updateAccount(updatedWithdrawAcc)),
      transfers = Transfer(newTransferId, from, to, amount) :: transfers
    )) match {
      case Left(errorMessage) => copy(
        failedTransfers = (Transfer(newTransferId, from, to, amount), errorMessage) :: failedTransfers
      )

      case Right(properlyUpdatedDb) => properlyUpdatedDb
    }
  }

  def newTransferId: Int = (transfers ++ failedTransfers.map(_._1)).map(_.id) match {
    case Nil => 1
    case list => list.max + 1
  }

  def findAccount(number: String): Either[String, Account] = banks.flatMap(_.findAccount(number))
    .headOption.toRight(s"No such account. number $number")

  override def toString: String =
    s"""banks:
       |${tab(banks.mkString("\n"))}
       |transfers:
       |${tab(transfers.mkString("\n"))}
       |failed transfers:
       |${tab(failedTransfers.map(f => s"${f._1} [${f._2}]").mkString("\n"))}
       |""".stripMargin
}
