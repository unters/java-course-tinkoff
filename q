[33mcommit 5080daf2631cd586a346a8d47177d3f1ee9df904[m[33m ([m[1;36mHEAD -> [m[1;32mproject1[m[33m)[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Fri Oct 20 01:01:35 2023 +0300

    Added RiddleWord class
    
    Added `RiddleWord` class to perform checks that given String object can be used as an an answer for "The Hangman" game only once. Resolved some issues with `GameSession`.

[33mcommit 8bb999fa450a4e4c2ba3726c7caaea664f16ee53[m[33m ([m[1;31morigin/project1[m[33m)[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Tue Oct 17 11:22:50 2023 +0300

    Fixed some issues
    
    1. Fixed issue with the program aborting after one session.
    2. GameSession now takes into consideration guesses that have already been made by the player - if the player enters the same wrong letter twice or multiple times it affects his mistakes counter only once when the letter was entered the first time.

[33mcommit e8cb3e3e215c52468324beb680c6697aff2f58c6[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Tue Oct 17 01:39:50 2023 +0300

    Added project1 files
    
    Added `GameManager`, `GameSession` and `Dictionary` classes. Added sample dictionary file `project1/dictionary.txt`.
    
    Issue: `NoSuchElementException` is thrown after one game session (independently of how the game was finished). Looks like the problem is caused by `System.in` stream being closed during the game session - `System.in` cannot be re-opened after.

[33mcommit 79bcdc0fe02ad6441d9ca0f6353078277a9058f4[m[33m ([m[1;31morigin/master[m[33m, [m[1;32mmaster[m[33m)[m
Merge: 3efff50 0e7fc6a
Author: unters <71709599+unters@users.noreply.github.com>
Date:   Sun Oct 8 22:05:24 2023 +0300

    Merge pull request #1 from unters/hw1
    
    Homework 1

[33mcommit 0e7fc6aa4efd680ee92420599089d899ecb32637[m[33m ([m[1;31morigin/hw1[m[33m, [m[1;32mhw1[m[33m)[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Sun Oct 8 21:58:15 2023 +0300

    Modified solution to task 2
    
    Created a 'private static final int BASE` variable.

[33mcommit 7e782ac4c1861175bc8f8b438f5b488751b459c1[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Sun Oct 8 20:38:45 2023 +0300

    Changed code according to pull request comments:
    
    Minor changes
    1. Changed build status link in README.md.
    2. Changed fields and methods definition order (`public static final fields` -> `private static final fields` -> `static initializers` -> `public static methods` -> private static methods`).
    3. Changed some variable's and method's names.
    4. Got rid of excess empty strings.
    5. Made changes to some comments.
    
    Task 1
    1. Changed method name from `minutesToSeconds(String s)` to `convertTimestampToSeconds(String timestamp)`.
    2. Got rid of `@SupressWarnings`.
    3. Changed `BigInteger` to `long`.
    4. Regex is now used to check that timestamp matches the input format.
    
    Task6
    1. Changed constant name from `K` to `KAPREKARS_CONSTANT`.
    2. Changed method name from `countK()` to `countInterationsInKeprekarsRoutine()`.
    
    Task 7
    1. `rotateRight()` now uses unsigned right shift operatior instead of right shift operator.
    2. Added comments.
    
    Tests
    1. All tests are now parameterized.
    2. Each annotation is now located in a separate line.

[33mcommit df6dfadd16d1859299afd95706e3d0b90c54230e[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Fri Oct 6 21:08:16 2023 +0300

    Changed solution (with tests) to task 7: rotateLeft() and rotateRight() now work with int instead of byte as they are supposed to be.

[33mcommit 564c01fa32eeab5c4a28531bdc3039aad2d653c4[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Thu Oct 5 18:16:31 2023 +0300

    Modified solution (and test) to task 1. Added check for minutes containing less than 2 characters.

[33mcommit 087d59c95414692f5dede316f11ae082abc9a09a[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Thu Oct 5 18:10:02 2023 +0300

    Modified solutions to match the codestyle.

[33mcommit 3efff50af72894371fa0c5e134b1eafde8f71039[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Thu Oct 5 14:57:24 2023 +0300

    Modified README.md.

[33mcommit 9a8d7888057c45a3505da932fde99270830b923c[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Thu Oct 5 14:56:01 2023 +0300

    Modified README.md.

[33mcommit b8f4e1d978f77dfe413b5d725bc5b3fcade7b31e[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Thu Oct 5 14:52:32 2023 +0300

    Added solutions (with tests) to tasks 0 and 5-8. Modified solutions (with tests) to tasks 3 and 4.

[33mcommit f542476fba41879d2a550738de29e141180d2d5f[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Wed Oct 4 23:48:07 2023 +0300

    Added solutions (with tests) to tasks 2-4.

[33mcommit 01da9d88582f1d5cd00802106cadfa978eb209b7[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Wed Oct 4 20:11:55 2023 +0300

    Added solution and tests to task 1.

[33mcommit 4b98d8cc71cfa1e27f359be93ff27cddbc08997a[m
Author: Artem Gromakov <gromakov.art3m@gmail.com>
Date:   Wed Oct 4 18:46:56 2023 +0300

    Initial commit.
