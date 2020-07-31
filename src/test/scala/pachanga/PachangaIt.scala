package pachanga

import cats._
import cats.implicits._

import cats.effect._
import org.http4s._
import org.http4s.client._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import org.http4s.client.blaze._
import org.http4s.client._
import scala.concurrent.ExecutionContext.global

import java.util.concurrent._
import pachanga.model.Pachanga

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

import org.http4s.Method



object  PachangaIt extends IOApp {

    val blockingPool = Executors.newFixedThreadPool(5)
    val blocker = Blocker.liftExecutorService(blockingPool)
    val httpClient: Client[IO] = JavaNetClientBuilder[IO](blocker).create


    def run(args: List[String]): IO[ExitCode] =  {
        val mainRes = for {
            //post <- Method.POST(Pachanga("koko", Seq()).asJson, uri"http://localhost:8080/pachanga/uno")
            get <- httpClient.expect[String]("http://localhost:8080/pachanga/uno")
            _ = println(s"Res $get")
        } yield get
        mainRes.as(ExitCode.Success)
    }




  
}
