package pachanga



import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import fs2.Stream
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import scala.concurrent.ExecutionContext.global
import cats.effect.IOApp
import pachanga.api.PachangaEndpoints
import cats.effect.IO
import cats.effect.ExitCode
import org.http4s.server.Router
import pachanga.repository.PachangaRepository
import pachanga.repository.memory.PachangaRepositoryInMemory
import pachanga.api.PlayerEndpoints
import pachanga.repository.memory.PlayerRepositoryInMemory

object Server extends IOApp {

  def stream[F[_]: ConcurrentEffect](implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
      val pachangaRepo = new PachangaRepositoryInMemory[F]()
      val playerRepo = new PlayerRepositoryInMemory[F]()
      val httpApp = Router(
          "/pachanga" -> PachangaEndpoints.endpoints[F](pachangaRepo),
          "/player" -> PlayerEndpoints.endpoints[F](playerRepo)
      ).orNotFound

      //val finalHttpApp = Logger.httpApp(true, true)(httpApp)
      val finalHttpApp = httpApp

      BlazeServerBuilder[F](global)
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
  }.drain

   def run(args: List[String]) =
    stream[IO].compile.drain.as(ExitCode.Success)
  
}
