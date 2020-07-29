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

class PachangaEndpoints[F[_]: Sync](repo: PachangaRepository[F]) extends Http4sDsl[F] {

    implicit val playerDecoder: EntityDecoder[F, Pachanga] = jsonOf[F, Pachanga]


    def pachangaRoutes(): HttpRoutes[F] = {
        HttpRoutes.of[F] {
            case GET -> Root / id => 
               for {
                   res <- repo.get(id)
                   json <- Ok(res.asJson)
                } yield json

            case req @ POST -> Root => 
                for {
                    pch <- req.as[Pachanga]
                    res <- repo.create(pch)
                    json <- Ok(res.asJson)
                } yield json
        }
    }

}

object PachangaEndpoints {
    def endpoints[F[_]: Sync](repo: PachangaRepository[F]): HttpRoutes[F] =
        new PachangaEndpoints[F](repo).pachangaRoutes()
}
