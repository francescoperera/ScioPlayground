import com.spotify.scio.{ContextAndArgs}

object Featran {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)
    val inputDataset = args("input")
    println(input)

    val result = sc.close().waitUntilFinish()

  }

}
