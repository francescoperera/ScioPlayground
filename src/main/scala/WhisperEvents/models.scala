package WhisperEvents

import io.circe._, io.circe.generic.semiauto._,io.circe.syntax._

case class WhisperPayload(userId: Option[String],
                          roleId: Option[String],
                          isActive: Option[Boolean],
                          roleName: Option[String],
                          range: Option[Int],
                          timestamp: Option[java.sql.Date]
                          )
object WhisperPayload {
  //implicit val whisperPayloadDecoder: Decoder[WhisperPayload] = deriveDecoder[WhisperPayload]
  implicit val whisperPayloadEncoder: Encoder[WhisperPayload] = Encoder.instance{
    case WhisperPayload( ui, ri, ia, rn, r ,t) => Json.obj(
        "userId" -> ui.asJson,
        "roleId" -> ri.asJson,
        "isActive" -> ia.asJson,
        "roleName" -> rn.asJson,
        "range" -> r.asJson,
        "timestamp" -> Json.fromString(t.getOrElse(None).toString)
      )
  }
}

case class Header(action: Option[String],
                  messageId: Option[String],
                  source: Option[String],
                  eventType: Option[String]
                 )

object Header {
  //implicit val headerDecoder: Decoder[Header] = deriveDecoder[Header]
  implicit val headerEncoder: Encoder[Header] = Encoder.instance{
    case Header(a, mi, s, et) => Json.obj(
      "action" -> a.asJson,
      "messageId" -> mi.asJson,
      "source" -> s.asJson,
      "eventType" -> et.asJson
    )
  }
}

case class WhisperEvent(headers: Header, payload:WhisperPayload)

object WhisperEvent {

  implicit  val whisperEventEncoder: Encoder[WhisperEvent] = Encoder.instance{
    case WhisperEvent( h, p) => Json.obj(
      "headers" -> h.asJson,
      "payload" -> p.asJson
    )
  }

}

case class Address(region: String,
                   country: String,
                   streetAddress: Option[String] = None,
                   extendedAddress: Option[String] = None,
                   postOfficeBox: Option[String] = None,
                   locality: Option[String] = None,
                   postalCode: Option[String] = None,
                   latitude: Option[Double] = None,
                   longitude: Option[Double] = None
                  )


case class Identity(classic: Option[Int] = None)

case class ProfilePayload(userId: Option[String] = None,
                                   classicLocale: Option[String] = None,
                                   classicCountry: Option[String] = None,
                                   firstName: Option[String] = None,
                                   lastName: Option[String] = None,
                                   identity: Option[Identity] = None,
                                   version: Option[Double] = None,
                                   active: Option[Boolean] = None,
                                   localdate: Option[String] = None,
                                   username: Option[String] = None,
                                   title: Option[String] = None,
                                   middleInitial: Option[String] = None,
                                   birthDate: Option[String] = None,
                                   gender: Option[String] = None,
                                   height: Option[Double] = None,
                                   address: Option[Map[String, Address]] = None,
                                   phone: Option[Map[String, String]] = None,
                                   email: Option[Map[String, String]] = None,
                                   //entitlements: Seq[String] = Nil,
                                   weighInDay: Option[String] = None,
                                   goalWeight: Option[Double] = None,
                                   goalWeightUnits: Option[String] = None,
                                   pointsProgram: Option[String] = None,
                                   trackingMode: Option[String] = None,
                                   weightLossMode: Option[String] = None,
                                   swappingMode: Option[String] = None,
                                   wpaAdjustment: Option[Double] = None,
                                   dptAdjustment: Option[Double] = None,
                                   nursingMother: Option[String] = None,
                                   preferredHeightWeightUnits: Option[String] = None,
                                   timezone: Option[String] = None,
                                   avatarUrl: Option[String] = None,
                                   hasCompletedOnboarding: Option[Boolean] = None,
                                   communicationPreference: Option[String] = None,
                                   newsletterOption: Option[Boolean] = None,
                                   acquisitionId: Option[String] = None,
                                   zipWork: Option[String] = None,
                                   referrerSite: Option[String] = None,
                                   registrationDate: Option[String] = None,
                                   enrollmentDate: Option[String] = None,
                                   additionalSettings: Option[Map[String, String]] = None,
                                   smartPointsActivationDate: Option[String] = None
                                  )

case class ProfileEvent(headers: Header, payload: ProfilePayload)

