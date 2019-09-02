package security

import controllers.StandardFormats
import javax.inject.Inject
import play.api.{Configuration, Logging}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

case class OAuthApp(id : String, clientId : String, clientSecret : String, authorizeUrl : String, tokenUrl : String, userInfoUrl : String, redirectUrl : Option[String])

case class DiscordUser(id : String, username : String, discriminator : String, avatar: String, bot : Option[Boolean], mfa_enabled : Boolean, locale : String, verified : Option[String])

class Security @Inject()(wsClient: WSClient, configuration: Configuration, securityUserRepo: SecurityUserRepo)(implicit ec : ExecutionContext) extends Logging with StandardFormats {
  val discordOAuth = OAuthApp(
    id = configuration.get[String]("security.discord.id"),
    clientId = configuration.get[String]("security.discord.clientId"),
    clientSecret = configuration.get[String]("security.discord.clientSecret"),
    authorizeUrl = configuration.get[String]("security.discord.authorizeUrl"),
    tokenUrl = configuration.get[String]("security.discord.tokenUrl"),
    userInfoUrl = configuration.get[String]("security.discord.userInfoUrl"),
    redirectUrl = configuration.get[Option[String]]("security.discord.redirectUrl")
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
