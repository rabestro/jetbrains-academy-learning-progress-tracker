package lv.id.jc.tracker.command

import lv.id.jc.tracker.model.Student
import lv.id.jc.tracker.repository.InMemoryDatabase
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class FindTest extends Specification {
    @Shared
    def mira = new Student('1', 'Mira', 'Sorvino', 'mira@harvard.edu')
    @Shared
    def sheryl = new Student('2', 'Sheryl', 'Sandberg', 'sheryl@facebook.com')

    def repository = new InMemoryDatabase([mira, sheryl])

    @Subject
    def underTest = new Find(repository)

    def 'should have a header'() {
        expect:
        with(underTest.header()) {
            isPresent()
            get() == "Enter an id or 'back' to return"
        }
    }

    def 'should find a student by id'() {
        expect:
        underTest.getResponse(id) == expected

        where:
        id  | expected
        '3' | "No student is found for id=$id"
        '1' | "$id points: Java=0; DSA=0; Databases=0; Spring=0"
        '2' | "$id points: Java=0; DSA=0; Databases=0; Spring=0"
    }
}
