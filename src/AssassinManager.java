import java.util.List;

/*
NOTE: To complete this assignment this is the ONLY file you need
      to make changes to. Also note, you do not need to add
      any additional class fields.

Instructions on how each method should work are written above each method.
The game should end when the kill ring only contains one player.


 */

public class AssassinManager {
    AssassinNode killRingHead;
    AssassinNode graveyardHead;

/*
This is your method for constructing an assassin manager
object. It should add the names from the list into the kill ring in
the same order in which they appear in the list. For example, if
the list contains {“John”, “Sally”, “Fred”}, then in the initial
kill ring we should see that John is stalking Sally who is
stalking Fred who is stalking John (reported in that order). You
may assume that the names are nonempty strings and that there
are no duplicate names (ignoring case). Your method should
throw an IllegalArgumentException if the list is empty.
*/
    public AssassinManager(List<String> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("List of names cannot be null or empty.");
        }

        // Create the kill ring linked list
        for (int i = names.size() - 1; i >= 0; i--) {
            killRingHead = new AssassinNode(names.get(i), killRingHead);
        }

    }

/*
This method should print the names of the people in the kill
ring, one per line, indented four spaces, with output of the form
“<name> is stalking <name>”. If there is only one person in
the ring, it should report that the person is stalking themselves
(e.g., “John is stalking John”).
*/
    public void printKillRing() {
        // Initialize current variable
        AssassinNode curr = killRingHead;
        while (curr != null) {
            if (curr != null && curr.next == null){
                curr.next = curr;
                System.out.printf("%s is stalking themselves.%n", curr.name);
            }
            else{
                System.out.print(curr.name + " is stalking ");
                System.out.println(curr.next != null ? curr.next.name : killRingHead.name);
                curr = curr.next;
            }
        }
        

    }

/*
This method should print the names of the people in the
graveyard, one per line, indented four spaces, with output of the
form “<name> was killed by <name>”. It should print the
names in reverse kill order (most recently killed first, then next
more recently killed, and so on). It should produce no output if
the graveyard is empty.
*/
    public void printGraveyard() {
        // Initialize current variable
        AssassinNode current = graveyardHead;
        while (current != null) {
            System.out.println(current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

/*
This should return true if the given name is in the current kill
ring and should return false otherwise. It should ignore case in
comparing names.
*/
    public boolean killRingContains(String name) {
        // Initialize current variable
        AssassinNode curr = killRingHead;
        while (curr != null) {
            if (curr.name.equalsIgnoreCase(name)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

/*
This should return true if the given name is in the current
graveyard and should return false otherwise. It should ignore
case in comparing names.
*/
    public boolean graveyardContains(String name) {
        // Initialize current variable
        AssassinNode curr = graveyardHead;
        while (curr != null) {
            if (curr.name.equalsIgnoreCase(name)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

/*
This should return true if the game is over (i.e., if the kill ring
has just one person in it) and should return false otherwise.
*/
    public boolean gameOver() {
        return killRingHead != null && killRingHead.next == null;
    }

/*
This should return the name of the winner of the game. It
should return null if the game is not over
*/
    public String winner() {
        return gameOver() ? killRingHead.name : null; // Returns a choice of the winner or null
    }

/*
This method records the killing of the person with the given
name, transferring the person from the kill ring to the
graveyard. This operation should not change the kill ring order
of printKillRing (i.e., whoever used to be printed first should
still be printed first unless that’s the person who was killed, in
which case the person who used to be printed second should
now be printed first). It should throw an
IllegalArgumentException if the given name is not part of the
curr kill ring and it should throw an IllegalStateException if
the game is over (it doesn’t matter which it throws if both are
true). It should ignore case in comparing names.
*/
    public void kill(String name) {
        if (!killRingContains(name)) {
            throw new IllegalArgumentException("Name is not in the kill ring."); // If the name is not present in the killring return string response
        }

        AssassinNode deceased = null;
        if (killRingHead.name.equalsIgnoreCase(name)) {
            // Deceased set as first node in the kill ring
            deceased = killRingHead;
            killRingHead = killRingHead.next;
        } else {
            AssassinNode curr = killRingHead;
            while (curr.next != null) {
                if (curr.next.name.equalsIgnoreCase(name)) {
                    deceased = curr.next;
                    curr.next = curr.next.next;
                    break;
                }
                curr = curr.next;
            }
        }

        if (deceased != null) {
            AssassinNode last = killRingHead;
            while (last.next != null) {
                last = last.next;
            }
            deceased.killer = last.name;

            // Move deceased to the graveyard
            deceased.next = graveyardHead;
            graveyardHead = deceased;
        }

    }
}
