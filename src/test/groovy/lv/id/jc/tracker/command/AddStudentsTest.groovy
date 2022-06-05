package lv.id.jc.tracker.command

import lv.id.jc.tracker.repository.TrackerRepository
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Command 'add students'")
class AddStudentsTest extends Specification {
    def repository = Mock TrackerRepository

    @Subject
    def underTest = new AddStudents(repository)

    def 'should returns a header'() {
        expect:
        with(underTest.header()) {
            isPresent()
            get() == "Enter student credentials or 'back' to return"
        }
    }

    def 'should returns a footer'() {
        expect:
        with(underTest.footer()) {
            isPresent()
            get() == "Total 0 students have been added."
        }
    }

}
