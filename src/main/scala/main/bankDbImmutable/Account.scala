package main.bankDbImmutable

case class Account(name: String, number: String, amount: Int) {
  def withdraw(amountToWithdraw: Int): Either[String, Account] =
    if (amountToWithdraw > amount)
      Left(s"Not enough money on $number to withdraw. Available: $amount, needed at least: $amountToWithdraw")
    else
      Right(copy(amount = amount - amountToWithdraw))

  def topup(amountToAdd: Int): Either[String, Account] =
    Right(copy(amount = amount + amountToAdd))

  override def toString: String = s"$number ( ${amount + "$"} ) [$name]"
}
