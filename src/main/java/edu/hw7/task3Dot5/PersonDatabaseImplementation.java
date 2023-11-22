package edu.hw7.task3Dot5;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* There is a requirement that a person can be found by one of its fields only when all other fields are not null.
 * The question is: why should we be able to add in a database a Person with some data missing in this case? My guess
 * is: there must be a possibility to extend current class and implement such methods that will allow to find a person
 * by one of its fields even if some data about this person is missing. Therefore, in current database implementation
 * the person is added in all the maps/sets/lists even if it has some fields equal to null.  */
public class PersonDatabaseImplementation implements PersonDatabase {
    @Override
    public final void add(Person person) {
        try {
            readWriteLock.writeLock().lock();

            if (persons.containsKey(person.id())) {
                this.delete(person.id());
            }

            persons.put(person.id(), person);

            if (person.name() != null) {
                if (nameToPersonsMap.containsKey(person.name())) {
                    nameToPersonsMap.get(person.name()).add(person);
                } else {
                    Set<Person> set = new HashSet<>();
                    set.add(person);
                    nameToPersonsMap.put(person.name(), set);
                }
            }

            if (person.address() != null) {
                if (addressToPersonsMap.containsKey(person.address())) {
                    addressToPersonsMap.get(person.address()).add(person);
                } else {
                    Set<Person> set = new HashSet<>();
                    set.add(person);
                    addressToPersonsMap.put(person.address(), set);
                }
            }

            if (person.phoneNumber() != null) {
                if (phoneNumberToPersonsMap.containsKey(person.phoneNumber())) {
                    phoneNumberToPersonsMap.get(person.phoneNumber()).add(person);
                } else {
                    Set<Person> set = new HashSet<>();
                    set.add(person);
                    phoneNumberToPersonsMap.put(person.phoneNumber(), set);
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public final void delete(int id) {
        try {
            readWriteLock.writeLock().lock();

            Person person = persons.get(id);
            if (person == null) {
                return;
            }

            persons.remove(id);

            if (person.name() != null) {
                nameToPersonsMap.get(person.name()).remove(person);
            }

            if (person.address() != null) {
                addressToPersonsMap.get(person.address()).remove(person);
            }

            if (person.phoneNumber() != null) {
                phoneNumberToPersonsMap.get(person.phoneNumber()).remove(person);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public final List<Person> findByName(String name) {
        try {
            readWriteLock.readLock().lock();

            Set<Person> personSet = nameToPersonsMap.get(name);
            if (personSet == null || personSet.isEmpty()) {
                return List.of();
            }

            List<Person> answer = new ArrayList<>();
            for (Person person : personSet) {
                if (personIsValid(person)) {
                    answer.add(person);
                }
            }

            return answer;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public final List<Person> findByAddress(String address) {
        try {
            readWriteLock.readLock().lock();

            Set<Person> personSet = addressToPersonsMap.get(address);
            if (personSet == null || personSet.isEmpty()) {
                return List.of();
            }

            List<Person> answer = new ArrayList<>();
            for (Person person : personSet) {
                if (personIsValid(person)) {
                    answer.add(person);
                }
            }

            return answer;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public final List<Person> findByPhone(String phoneNumber) {
        try {
            readWriteLock.readLock().lock();

            Set<Person> personSet = phoneNumberToPersonsMap.get(phoneNumber);
            if (personSet == null || personSet.isEmpty()) {
                return List.of();
            }

            List<Person> answer = new ArrayList<>();
            for (Person person : personSet) {
                if (personIsValid(person)) {
                    answer.add(person);
                }
            }

            return answer;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private boolean personIsValid(Person person) {
        return person.name() != null && person.address() != null && person.phoneNumber() != null;
    }

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Map<Integer, Person> persons = new HashMap<>();
    private final Map<String, Set<Person>> nameToPersonsMap = new HashMap<>();
    private final Map<String, Set<Person>> addressToPersonsMap = new HashMap<>();
    private final Map<String, Set<Person>> phoneNumberToPersonsMap = new HashMap<>();
}
