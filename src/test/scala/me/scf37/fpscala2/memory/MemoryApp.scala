package me.scf37.fpscala2.memory

import java.sql.Connection

import cats.Monad
import cats.effect.Effect
import me.scf37.fpscala2.Application
import me.scf37.fpscala2.module.Later
import me.scf37.fpscala2.module.DaoModule
import me.scf37.fpscala2.module.DbModule
import me.scf37.fpscala2.db.SqlEffectLift
import me.scf37.fpscala2.db.sql.SqlEffectEval
import me.scf37.fpscala2.db.sql.SqlEffectLift
import me.scf37.fpscala2.memory.MemoryApp._
import me.scf37.fpscala2.module.config.ApplicationConfig

class MemoryApp[I[_]: Later: Monad, F[_]: Effect](
) extends Application[I, F, F](ApplicationConfig.testConfig) {

  override lazy val daoModule: DaoModule[F, I] = new MemoryDaoModule[F, I]

  override lazy val dbModule: DbModule[F, F, I] = new MemoryDbModule[F, I]
}

object MemoryApp {
  implicit def DB[F[_]]: SqlEffectLift[F, F] = new SqlEffectLift[F, F] {
    override def lift[A](f: Connection => F[A]): F[A] = ???
  }

  implicit def DE[F[_]]: SqlEffectEval[F, F] = new SqlEffectEval[F, F] {
    override def eval[A](f: F[A], c: Connection): F[A] = ???
  }

}
