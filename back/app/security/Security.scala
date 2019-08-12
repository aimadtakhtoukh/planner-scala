package security

import controllers.StandardFormats
import javax.inject.Inject
import play.api.Logging
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

case class OAuthApp(id : String, clientId : String, clientSecret : String, authorizeUrl : String, tokenUrl : String, userInfoUrl : String, redirectUrl : Option[String])

case class DiscordUser(id : String, username : String, discriminator : String, avatar: String, bot : Option[Boolean], mfa_enabled : Boolean, locale : String, verified : Option[String])

class Security @Inject()(wsClient: WSClient, securityUserRepo: SecurityUserRepo)(implicit ec : ExecutionContext) extends Logging with StandardFormats {
  val discordOAuth = OAuthApp(
    id = "DISCORD",
    clientId = "609309569721565184",
    clientSecret = "gm5V1XzyeccVOvLLtZ3b-g1d1kkBfEtl",
    authorizeUrl = "https://discordapp.com/api/oauth2/authorize",
    tokenUrl = "https://discordapp.com/api/oauth2/token",
    userInfoUrl = "https://discordapp.com/api/users/@me",
    redirectUrl = None
  )

  def getDiscordUserFromToken(token : String) : Future[Option[DiscordUser]] = {
    wsClient
      .url(discordOAuth.userInfoUrl)
      .withHttpHeaders("Authorization" -> token)
      .get()
      .map(_.body)
      .map(Json.parse)
      .map(_.asOpt[DiscordUser])

  }

}
