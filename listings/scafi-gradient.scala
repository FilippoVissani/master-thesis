object MyAggregateProgram extends AggregateProgram {

  override def main() = gradient(isSource)

  def gradient(isSource: Boolean): Double =
    rep(Double.PositiveInfinity)(distance =>
      mux(isSource){
        0.0
      }{
        minHoodPlus(nbr{distance} + nbrRange)
      }
  )

  def isSource = sense[Boolean]("source")
  def nbrRange = nbrvar[Double]("nbrRange")
}
