package pachanga.api

import cats._
import cats.implicits._

import cats.effect.Sync
import cats.effect.IO

import pachanga.repository.PachangaRepository
import pachanga.model.Pachanga

import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._

import org.http4s.EntityDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import pachanga.repository.PlayerRepository
import pachanga.model.Player

class PlayerEndpoints[F[_]: Sync](repo: PlayerRepository[F]) extends Http4sDsl[F] {

    implicit val playerDecoder: EntityDecoder[F, Player] = jsonOf[F, Player]


    def routes(): HttpRoutes[F] = {
        HttpRoutes.of[F] {
            case GET -> Root / id =>  {
               for {
                   res <- repo.get(id)
                   json <- res match {
                       case Some(v) => Ok(v.asJson)
                       case None => NotFound()
                   }
                } yield json
            }

            case req @ POST -> Root => 
                for {
                    pch <- req.as[Player]
                    res <- repo.create(pch)
                    json <- Ok(res.asJson)
                } yield json
        }
    }

}

object PlayerEndpoints {
    def endpoints[F[_]: Sync](repo: PlayerRepository[F]): HttpRoutes[F] =
        new PlayerEndpoints[F](repo).routes()
}
