package lv.id.jc.tracker.command

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('Abstract Command')
@Narrative('Tests for base logic for all commands')
class AbstractCommandTest extends Specification {

    @Subject
    def underTest = new AbstractCommand() {

        @Override
        String getResponse(String request) {
            return null
        }
    }

    def 'should finish execution if user enters "back"'() {
        expect:
        !underTest.runningCondition().test(request)

        where:
        request << ['back', 'BACK', 'Back', 'bAcK']
    }

    def 'should continue execution for requests other then "back"'() {
        expect:
        underTest.runningCondition().test(request)

        where:
        request << ['some command', '', 'exit', 'other command', 'quit']
    }
}
