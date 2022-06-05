package lv.id.jc.tracker.command

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import java.util.function.Predicate

@Title('Interface of application commands')
@Narrative('Tests for base logic for all commands')
class CommandTest extends Specification {

    @Subject
    def underTest = new Command() {

        @Override
        String getResponse(String request) {
            return null
        }
    }

    def 'should be runnable'() {
        expect:
        underTest instanceof Runnable
    }

    def 'should returns an empty header'() {
        expect:
        underTest.header().isEmpty()
    }

    def 'should returns an empty footer'() {
        expect:
        underTest.footer().isEmpty()
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
