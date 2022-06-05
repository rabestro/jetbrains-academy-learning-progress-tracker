package lv.id.jc.tracker.command

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import java.util.function.Predicate

@Title('Interface of application commands')
@Narrative('Tests for default methods for the interface')
class CommandTest extends Specification {

    @Subject
    def underTest = new Command() {

        @Override
        String getResponse(String request) {
            return null
        }

        @Override
        Predicate<String> runningCondition() {
            return null
        }

        @Override
        void run() {

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

}
