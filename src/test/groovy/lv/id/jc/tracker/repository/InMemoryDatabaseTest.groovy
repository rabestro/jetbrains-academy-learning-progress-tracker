package lv.id.jc.tracker.repository

import lv.id.jc.tracker.model.Student
import spock.lang.Shared
import spock.lang.Specification

class InMemoryDatabaseTest extends Specification {

    @Shared
    def mira = new Student('1', 'Mira', 'Sorvino', 'mira@harvard.edu')
    @Shared
    def sheryl = new Student('2', 'Sheryl', 'Sandberg', 'sheryl@facebook.com')

    def 'should save a student'() {
        given:
        def storage = Mock List

        and:
        def underTest = new InMemoryDatabase(storage)

        when:
        underTest.save(sheryl)

        then:
        1 * storage.add(sheryl)
    }

    def 'should check email existence'() {
        given:
        def repository = new InMemoryDatabase([mira, sheryl])

        when:
        def actual = repository.existsByEmail(email)

        then:
        actual == expected

        where:
        email               | expected
        mira.email()        | true
        sheryl.email()      | true
        'unknown@email.com' | false

    }

    def 'should find a student by id'() {
        given:
        def repository = new InMemoryDatabase([mira, sheryl])

        when:
        def actual = repository.findById(id)

        then:
        actual == expected

        where:
        id  | expected
        '1' | Optional.of(mira)
        '2' | Optional.of(sheryl)
        '0' | Optional.empty()
    }

    def 'should find all students'() {
        given:
        def repository = new InMemoryDatabase([mira, sheryl])

        when:
        def actual = repository.findAll()

        then:
        actual.toList() == [mira, sheryl]
    }

    def 'should find all ids'() {
        given:
        def repository = new InMemoryDatabase([mira, sheryl])

        when:
        def actual = repository.findAllIds()

        then:
        actual == ['1', '2']
    }

    def "FindAllTasks"() {
    }

    def "FindByCourse"() {
    }
}
