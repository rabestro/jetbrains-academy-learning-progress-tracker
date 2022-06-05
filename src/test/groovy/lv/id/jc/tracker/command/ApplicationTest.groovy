package lv.id.jc.tracker.command

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('Application main menu')
class ApplicationTest extends Specification {
    final CMD_ONE = 'one'

    def cmdOne = Mock Command

    @Subject
    def mainMenu = new Application([(CMD_ONE): cmdOne])

    def 'should execute known command'() {
        when:
        def response = mainMenu.getResponse(CMD_ONE)

        then:
        1 * cmdOne.run()

        and:
        response.isEmpty()
    }

    def 'should return appropriate error message'() {
        when:
        def actual = mainMenu.getResponse(request)

        then:
        actual == expected

        where:
        request | expected
        ''      | 'No input.'
        '    '  | 'No input.'
        'back'  | "Enter 'exit' to exit the program"
        'BACK'  | "Enter 'exit' to exit the program"
        '@#$%'  | "Error: unknown command!"
    }

    def 'should finish execution if user enters "exit"'() {
        expect:
        !mainMenu.runningCondition().test(request)

        where:
        request << ['exit', 'EXIT', 'Exit', 'eXiT']
    }

    def 'should continue execution for requests other then "exit"'() {
        expect:
        mainMenu.runningCondition().test(request)

        where:
        request << ['some command', '', 'back', 'other command', 'quit']
    }

    def 'should have a header'() {
        expect:
        with(mainMenu.header()) {
            isPresent()
            get() == 'Learning Progress Tracker'
        }
    }

    def 'should have a footer'() {
        expect:
        with(mainMenu.footer()) {
            isPresent()
            get() == 'Bye!'
        }
    }
}
