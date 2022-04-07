package main.bankDbImmutable

case class Transfer(id: Int, from: String, to: String, amount: Int) {
  override def toString: String = s"$id). $from --> $to ($amount" + "$)"
}
