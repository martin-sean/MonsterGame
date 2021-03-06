import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner 
{
	public static void main(String[] args) 
	{
		System.out.println("Starting up JUnit tests...");

		Result result;

		/*
		 * MonsterAiTest
		 */
		System.out.println("\n--------------------MonsterAiTest--------------------\n");

		result = JUnitCore.runClasses(MonsterAiTest.class);

		for (Failure failure : result.getFailures())
			System.out.println(failure.toString());

		System.out.println("\nWas this JUnit test succesful? " + result.wasSuccessful());

		/*
		 * PlayersTest
		 */
		System.out.println("\n--------------------PlayersTest--------------------");

		result = JUnitCore.runClasses(PlayersTest.class);

		for (Failure failure : result.getFailures())
			System.out.println(failure.toString());

		System.out.println("\nWas this JUnit test succesful? " + result.wasSuccessful());

		/*
		 * ConnHandlerTest
		 */
		System.out.println("\n--------------------ConnHandlerTest--------------------\n");

		System.out.println("Please use the sockecho program and point it at this"
				+ " device on port 3216 to complete the first test");

		result = JUnitCore.runClasses(ConnHandlerTest.class);

		for (Failure failure : result.getFailures())
			System.out.println(failure.toString());

		System.out.println("\nWas this JUnit test succesful? " + result.wasSuccessful());
	}
}