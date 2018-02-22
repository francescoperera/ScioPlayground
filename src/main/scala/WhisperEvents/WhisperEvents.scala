package WhisperEvents

import java.util.UUID

import com.spotify.scio.ContextAndArgs
import com.spotify.scio.values.SCollection
import io.circe.syntax._
import io.circe.{Json, JsonObject, parser}
import cats.syntax.either._
import io.circe.generic.auto._


object WhisperEvents {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    //TODO: Remove
    val input: String = "tmp/example_uuids.json/part-00001-of-00005.txt"

    val inputProfileEvents: SCollection[String] = sc.textFile(input)
    inputProfileEvents
    val jsonProfileEvents: SCollection[Json] = inputProfileEvents.flatMap(jsonString => toJson(jsonString))
    val profileEvents: SCollection[ProfileEvent] = jsonProfileEvents.flatMap(j => toProfileEvent(j))
    //.asObject.get.apply("payload").get.asObject.get.apply("userId"))
    //val userIds =  profileEvents.flatMap(_.payload.userId)
    val whisperEvents = profileEvents.map( event =>
      buildWhisperEvent(event.payload.userId, Some("ww-all"), Some("ww-all-id")).asJson.noSpaces)
    whisperEvents.saveAsTextFile("tmp3/json")
    sc.close()
  }

  def extractUserId( rawScollection: SCollection[String]) : SCollection[Json] = {
    rawScollection.flatMap(row => toJson(row))

  }

  def toJson(str: String) : Option[Json] = parser.parse(str).toOption
//  match {
//    case Left(failure) => None
//    case Right(json) => Some(json)
//  }

  def toProfileEvent(event: Json) : Option[ProfileEvent] = event.as[ProfileEvent].right.toOption

  def buildWhisperEvent(userId: Option[String],
                        role: Option[String],
                        roleId: Option[String]
                       ) = {

    def bucketUser(userId: Option[String]) : Option[Int] =
      userId match {
        case None => None
        case Some(id) => Some(UUID.fromString(id).hashCode() & 0x7FFFFFFF % 100)
      }

    WhisperEvent(
      Header(None,None,None,None),
      WhisperPayload(userId = userId,
                      roleId = roleId,
                      isActive = Some(true),
                      roleName = role ,
                      range = bucketUser(userId),
                      timestamp = Some(new java.sql.Date(System.currentTimeMillis())))
    )
  }

}


