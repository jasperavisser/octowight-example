package nl.haploid.octowight.channel.scout.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def atom = new Atom(nextLong, nextString, nextString)

  def atomChangeEvent: AtomChangeEvent = atomChangeEvent(nextLong)

  def atomChangeEvent(atomId: Long) = new AtomChangeEvent(id = atomId, origin = nextString, category = "person")

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceCollection = nextString, root = atom, version = null)
}
