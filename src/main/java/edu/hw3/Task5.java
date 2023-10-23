package edu.hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class Task5 {
    private static final String ASC_FLAG = "ASC";
    private static final String DESC_FLAG = "DESC";

    private static final String FULL_NAME_REGEX = "[A-Z][a-z]* [A-Z][a-z]*";
    private static final String SINGLE_NAME_REGEX = "[A-Z][a-z]*";

    public static List<Contact> buildSortedContactsList(List<String> fullNamesList, String sortingOrder) {
        if (!(ASC_FLAG.equalsIgnoreCase(sortingOrder) || DESC_FLAG.equalsIgnoreCase(sortingOrder))) {
            throw new IllegalArgumentException("Unknown sorting order: " + sortingOrder);
        }

        if (fullNamesList == null || fullNamesList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Contact> contacts = new ArrayList<>();
        for (String fullName : fullNamesList) {
            if (Pattern.matches(FULL_NAME_REGEX, fullName)) {
                int index = fullName.indexOf(' ');
                contacts.add(new Contact(fullName.substring(0, index), fullName.substring(index + 1)));
            } else if (Pattern.matches(SINGLE_NAME_REGEX, fullName)) {
                contacts.add(new Contact(fullName, null));
            } else {
                throw new IllegalArgumentException("Wrong full name format: " + fullName);
            }
        }

        contacts.sort(ASC_FLAG.equalsIgnoreCase(sortingOrder) ? null : Collections.reverseOrder());
        return contacts;
    }

    private Task5() {
    }

    public record Contact(String name, String surname) implements Comparable<Contact> {
        @Override
        public int compareTo(@NotNull Contact anotherContact) {
            if (this.surname != null && anotherContact.surname() != null) {
                return this.surname.compareTo(anotherContact.surname());
            }

            if (this.surname == null && anotherContact.surname() == null) {
                return this.name.compareTo(anotherContact.name());
            }

            if (this.surname == null) {
                return this.name.compareTo(anotherContact.surname());
            }

            return this.surname.compareTo(anotherContact.name());
        }
    }
}
