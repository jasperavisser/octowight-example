package nl.haploid.octowight.emitter.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.emitter.sample.repository.AtomChangeEventDmo

object TestData {

  val AtomLocus = nextString

  val AtomCategory = nextString

  def atomChangeEventDmo = AtomChangeEventDmo(id = nextLong, atomId = nextLong, atomOrigin = nextString, atomCategory = nextString)

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString
}
