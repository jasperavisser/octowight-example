package nl.haploid.octowight.source.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def name = nextString

  def nextString = UUID.randomUUID.toString

  def nextLong: Long = new Random().nextLong

  def resourceRoot =
    new ResourceRoot(resourceId = nextLong, resourceCollection = nextString, root = atom, version = null)

  def atom = new Atom(nextLong, nextString, nextString)
}
