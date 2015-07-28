package nl.haploid.octowight.channel.scout.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def atomChangeEvent: AtomChangeEvent = atomChangeEvent(nextLong)

  def atomChangeEvent(atomId: Long) =
    new AtomChangeEvent(id = nextLong, atomId = atomId, atomOrigin = nextString, atomCategory = "person")

  def name = nextString

  def nextLong: Long = new Random().nextLong

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceCollection = nextString, root = atom, version = null)

  def atom = new Atom(nextLong, nextString, nextString)

  def topic = nextString

  def nextString = UUID.randomUUID.toString
}
