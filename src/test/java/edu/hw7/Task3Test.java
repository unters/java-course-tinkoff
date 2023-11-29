package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import edu.hw7.task3.PersonDatabaseImplementation;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Task3Test {
    @Test
    void add_PersonWithAllFieldsNotNullGiven_PersonCanBeFoundByAnyOfItsFields() {
        // given
        int id = 1;
        String name = "Alyx Vance";
        String address = "City 17";
        String phoneNumber = "+7 (123) 456-78-90";
        Person person = new Person(1, name, address, phoneNumber);
        PersonDatabase personDatabase = new PersonDatabaseImplementation();

        // when
        personDatabase.add(person);
        List<Person> personsByName = personDatabase.findByName(name);
        List<Person> personsByAddress = personDatabase.findByAddress(address);
        List<Person> personsByPhoneNumber = personDatabase.findByPhone(phoneNumber);

        // then
        assertThat(personsByName.contains(person)).isTrue();
        assertThat(personsByAddress.contains(person)).isTrue();
        assertThat(personsByPhoneNumber.contains(person)).isTrue();
    }

    @Test
    void add_PersonWithSomeFieldsMissingGivenToEmptyDatabase_PersonCanNotBeFoundByAnyOfItsFields() {
        // given
        int id = 1;
        String name = "Alyx Vance";
        String address = null;
        String phoneNumber = "+7 (123) 456-78-90";
        Person person = new Person(1, name, address, phoneNumber);
        PersonDatabase personDatabase = new PersonDatabaseImplementation();

        // when
        personDatabase.add(person);
        List<Person> personsByName = personDatabase.findByName(name);
        List<Person> personsByAddress = personDatabase.findByAddress(address);
        List<Person> personsByPhoneNumber = personDatabase.findByPhone(phoneNumber);

        // then
        assertThat(personsByName.isEmpty()).isTrue();
        assertThat(personsByAddress.isEmpty()).isTrue();
        assertThat(personsByPhoneNumber.isEmpty()).isTrue();
    }

    @Test
    void delete_PersonIsAddedAndThenDeleted_PersonCanNotBeFoundByAnyOfItsFields() {
        // given
        int id = 1;
        String name = "Alyx Vance";
        String address = "City 17";
        String phoneNumber = "+7 (123) 456-78-90";
        Person person = new Person(1, name, address, phoneNumber);
        PersonDatabase personDatabase = new PersonDatabaseImplementation();
        personDatabase.add(person);

        // when
        personDatabase.delete(id);
        List<Person> personsByName = personDatabase.findByName(name);
        List<Person> personsByAddress = personDatabase.findByAddress(address);
        List<Person> personsByPhoneNumber = personDatabase.findByPhone(phoneNumber);

        // then
        assertThat(personsByName.contains(person)).isFalse();
        assertThat(personsByAddress.contains(person)).isFalse();
        assertThat(personsByPhoneNumber.contains(person)).isFalse();
    }

    @Test
    void personDatabaseImplementation_MultipleThreadsPerformOperationsOnASingleDatabase_RaceConditionDoesNotOccur() {
        PersonDatabase personDatabase = new PersonDatabaseImplementation();

        final int iterationsNumber = 1_000_000;
        Runnable addingRunnable = () -> {
            for (int i = 0; i < iterationsNumber; ++i) {
                personDatabase.add(generateNewPerson());
            }
        };

        Runnable deletingRunnable = () -> {
            for (int i = 0; i < iterationsNumber; ++i) {
                personDatabase.delete(ThreadLocalRandom.current().nextInt(100));
            }
        };

        Thread addingThread1 = new Thread(addingRunnable);
        Thread addingThread2 = new Thread(addingRunnable);
        Thread deletingThread1 = new Thread(deletingRunnable);
        Thread deletingThread2 = new Thread(deletingRunnable);
        assertDoesNotThrow(() -> {
            addingThread1.start();
            addingThread2.start();
            deletingThread1.start();
            deletingThread2.start();

            addingThread1.join();
            addingThread2.join();
            deletingThread1.join();
            deletingThread2.join();
        });
    }

    private static final String[] names = new String[] {
        "Richard Feynman", "Andrey Kolmogorov", "Vladimir Arnold", "Alan Turing", "Karl Gauss", "Herbert Shildt"
    };

    private static final String[] addresses = new String[] {
        "address1", "address2", "address3", "address4", "address5", "address6", "address7", "address8", "address9"
    };

    private static final String[] phoneNumbers = new String[] {
        "phone number 1", "phone number 2", "phone number 3", "phone number 4", "phone number 5", "phone number 6",
    };

    private static Person generateNewPerson() {
        int id = ThreadLocalRandom.current().nextInt(100);
        String name =  (ThreadLocalRandom.current().nextInt(10) < 8)
            ? names[ThreadLocalRandom.current().nextInt(names.length)]
            : null;
        String address = (ThreadLocalRandom.current().nextInt(10) < 8)
            ? addresses[ThreadLocalRandom.current().nextInt(addresses.length)]
            : null;
        String phoneNumber = (ThreadLocalRandom.current().nextInt(10) < 8)
            ? phoneNumbers[ThreadLocalRandom.current().nextInt(phoneNumbers.length)]
            : null;
        return new Person(id, name, address, phoneNumber);
    }
}
