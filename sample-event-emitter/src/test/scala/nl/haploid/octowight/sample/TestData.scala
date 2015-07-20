package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.sample.repository.AtomChangeEventDmo

object TestData {
  val AtomLocus = nextString
  val AtomCategory = nextString

  def atomChangeEventDmo = AtomChangeEventDmo(id = nextLong, atomId = nextLong, atomOrigin = nextString, atomCategory = nextString)

  def nextLong: Long = new Random().nextLong

  def topic = nextString

  def nextString = UUID.randomUUID.toString
}
