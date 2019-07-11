package security

import javax.inject.Inject
import play.api.Logging
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

case class OAuthApp(id : String, clientId : String, clientSecret : String, authorizeUrl : String, tokenUrl : String, userInfoUrl : String, redirectUrl : Option[String])

case class DiscordUser(id : String, username : String, discriminator : String, avatar: String, bot : Boolean, mfaEnabled : Boolean, locale : String, verified : String)

class Security @Inject()(wsClient: WSClient)(implicit ec : ExecutionContext) extends Logging {
  val discordOAuth = OAuthApp(
    id = "DISCORD",
    clientId = "468461306031243264",
    clientSecret = "KOjFMglvFPVrN_ZWABqIjmrFaX7EAtAE",
    authorizeUrl = "https://discordapp.com/api/oauth2/authorize",
    tokenUrl = "https://discordapp.com/api/oauth2/token",
    userInfoUrl = "https://discordapp.com/api/users/@me",
    redirectUrl = None
  )

  def getDiscordUserFromToken(token : String) : Future[Option[DiscordUser]] = {
    wsClient
      .url(discordOAuth.userInfoUrl)
      .withHttpHeaders("Authorization" -> s"Bearer $token")
      //.withAuth(discordOAuth.clientId, discordOAuth.clientSecret, BASIC)
      //.post(Map("grant_type" -> Seq("authorization_code"), "code" -> Seq(token), "scope" -> Seq("identify")))
      .get()
      .map(result => {
        logger.error(result.status + "")
        logger.error(result.body)
        None
      })
  }

}
