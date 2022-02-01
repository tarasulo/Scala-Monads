package userService
import userService.Monad._
import java.util.Scanner

object InputTest extends App {

  class IO[T](line: => T) {
    def run(): T = this.line
  }

  object IO {
    def apply[T](line: => T): IO[T] = new IO(line)

    def input(): IO[String] = IO (new Scanner(System.in).nextLine())

    def output(line: String): IO[Unit] = IO(println(line))
  }

  implicit object MonadIO extends Monad[IO] {
    override def unit[T](t: T): IO[T] = IO(t)

    override def flatMap[T, A](t: IO[T])(f: T => IO[A]): IO[A] = IO(f(t.run()).run())

    override def map[T, A](t: IO[T])(f: T => A): IO[A] = IO(f(t.run()))
  }

  val io: IO[String] = for {
    _ <- IO.output("Input line:")
    line <- IO.input()
    _ <- IO.output(line)
  } yield line

  io.run()

}
