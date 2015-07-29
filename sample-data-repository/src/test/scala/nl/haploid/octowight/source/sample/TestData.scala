package nl.haploid.octowight.source.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def atom = new Atom(nextLong, nextString, nextString)

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resourceRoot =
    new ResourceRoot(resourceId = nextLong, resourceCollection = nextString, root = atom, version = null)
}
